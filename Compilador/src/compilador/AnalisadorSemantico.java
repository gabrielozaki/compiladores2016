package compilador;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class AnalisadorSemantico {

        public static enum ErroSemanticoTipo {
            NDecl("não declarado(a)"),
            MultDecl("declarado(a) mais de uma vez"),
            NParam("número de parâmetros"),
            TParam("tipo de parâmetros"),
            OParam("ordem de parâmetros"),
            VEscopo("fora de escopo"),
            Tipo("atribuição de tipos incompatíveis"),
            NUtil("não utilizado(a)"),
            Div("div que não é entre inteiros"),
            Read("leitura de tipo incompatível"),
            Write("escrita de tipo incompatível");
            public final String erro;

            // E considerada uma boa pratica de programacao proteger o construtor do
            // enum
            // Garantindo assim tipos estaticos
            private ErroSemanticoTipo(String erro){
                this.erro = erro;
            }
	}
	// Esse é um contador de Id e não um id
	// Para cada procedimento declarado será incrementado um id
	// Isso vai servir para verificarmos o escopo das variaveis
	int id = 0;
        // Índice da primeira variável a ser declarada da lista de variáveis
        // -1 se nenhuma variável está sendo declarada
        int var_anchor_index = -1;
        // Índice do primeiro secparam a ser declarada da lista de parametros
        // -1 se nenhum parametro está sendo declarada
        int param_anchor_index = -1;
        // variável utilizada para verificar se está sendo declarada uma
        // variável ou um parâmetro
        boolean declarating_var = true;
        // tipo atual da variável a ser declarada
        // null se não há nehum tipo
        TokenSemanticoVariavel.TipoVar var_current_type = null;
        
        // lexema de variavel ou procedimento que receberá um valor ou 
        // executará um proc
        Token lex_var_or_proc = null;
        
        // Procedure atual
        TokenSemanticoProcedimento proc_curr = null;
        
        // Variável atual
        TokenSemanticoVariavel var_curr = null;
        
        // termo atual para verificar tipo em expressão
        TokenSemanticoVariavel.TipoVar var_type_curr = null;
        
        // endereco relativo de variaveis globais
        public int end_rel_global = -1;
        
        // variavel de rotulos
        public int rotulos = -1;
	// Lista de Tokens de variaveis e de procedimentos, basicamente seria as
	// nossas tabelas
	// São separadas para acelerar e facilitar as buscas
	ArrayList<TokenSemanticoProcedimento> procedimentos = new ArrayList<>();
	ArrayList<TokenSemanticoVariavel> variaveis = new ArrayList<>();
        ArrayList<String> erros_semanticos = new ArrayList<>();
        // lista de comandos para geracao de codigo
        ArrayList<Comando> comandos = new ArrayList<>();

	public AnalisadorSemantico() {
            inicializaReservados();
	}
        public void inicializaReservados(){
            Token read = new Token("read", 0, 0, 0);
            Token write = new Token("write", 0, 0, 0);
            
            Token t_true = new Token("true", 0, 0, 0);
            Token t_false = new Token("false", 0, 0, 0);
            procedimentos.add(
                    new TokenSemanticoProcedimento(read)
            );
            procedimentos.add(
                    new TokenSemanticoProcedimento(write)
            );
            end_rel_global++;
            variaveis.add(
                    new TokenSemanticoVariavel(
                            t_true,
                            TokenSemanticoVariavel.TipoVar.Booleano,
                            "true",
                            0,
                            end_rel_global
                    )
            );
            end_rel_global++;
            variaveis.add(
                    new TokenSemanticoVariavel(
                            t_false,
                            TokenSemanticoVariavel.TipoVar.Booleano,
                            "false",
                            0,
                            end_rel_global
                    )
            );
        }
	// Adiciona novos procedimentos
	public void adicionaProcedimento(Token t) {
            // Verifica caso o procedimento já existe, pois a linguagem não permite
            // declarar um procedimento duas vezes
            // Logo isso é um erro semantico
            if (verificaExistenciaProcedimento(t)) {
                logErro(t, ErroSemanticoTipo.MultDecl);
            }
            else{
                procedimentos.add(new TokenSemanticoProcedimento(t));
                id++;
            }

	}

	public void adicionaVariavel(Token t) {
		int aux;
                // declarando variável
                if (declarating_var){
                    // Verifica caso a variavel já existe naquele escopo
                    if (verificaExistenciaVar(t, id, variaveis)) {
                        // variável declarada mais de uma vez                        
                        logErro(t, ErroSemanticoTipo.Tipo.MultDecl);
                    }
                    else{
                        TokenSemanticoVariavel ts_aux;
                        ts_aux = new TokenSemanticoVariavel(t);
                        ts_aux.escopo = id;
                        ts_aux.tipo_var = var_current_type;
                        // adicionando endereço relativo para variaveis globais
                        if (ts_aux.escopo == 0){
                            end_rel_global++;
                            ts_aux.end_rel = end_rel_global;
                            gerar("", Comando.TipoComando.AMEM, "1");
                        }
                        variaveis.add(ts_aux);
                        
                    }
                }
                // declarando parâmetros
                else{
                    // precisamos da última procedure declarada
                    TokenSemanticoProcedimento last_proc;
                    last_proc = lastProcDecl();
                    if (verificaExistenciaParam(t, id, last_proc.parametros)) {
                        // variável declarada mais de uma vez                        
                        logErro(t, ErroSemanticoTipo.Tipo.MultDecl);
                    }
                    else{
                        TokenSemanticoVariavel ts_aux;
                        ts_aux = new TokenSemanticoVariavel(t);
                        ts_aux.escopo = id;
                        ts_aux.tipo_var = var_current_type;
                        last_proc.parametros.add(ts_aux);
                    }
                    
                }
	}
        //Importante para verificar se um procedimento foi criado antes
	public boolean verificaExistenciaProcedimento(Token t) {
		for (TokenSemanticoProcedimento p : procedimentos) {
			// Verifica se o procedimento já existe naquele escopo
			if (p.lexema == t.lexema) {
				return true;
			}
		}
		return false;
	}
	public boolean verificaExistenciaVar(
                Token t,
                int escopo,
                ArrayList<TokenSemanticoVariavel> var_array
            ) {
            /*for (int i=var_anchor_index; i<var_array.size(); i++){
                TokenSemanticoVariavel v = var_array.get(i);
                if (v.lexema.equals(t.lexema) && (v.escopo == escopo)) {
                    return true;
                }
            }*/
            for (TokenSemanticoVariavel v : variaveis) {
                    // Verifica se a variavel já existe naquele escopo
                    if (v.lexema.equals(t.lexema) && (v.escopo == escopo)) {
                            return true;
                    }
            }
            return false;
	}
        public boolean verificaExistenciaParam(
                Token t,
                int escopo,
                ArrayList<TokenSemanticoVariavel> var_array
            ) {
            System.out.println("existeEEEEEE");
            System.out.print(escopo);
            System.out.print(t.lexema);
            for (int i=0; i<var_array.size(); i++){
                TokenSemanticoVariavel v = var_array.get(i);
                if (v.lexema.equals(t.lexema)) {
                    return true;
                }
            }
            return false;
	}

	// Retorna o indice da variavel buscada
        // Retorna -1 se não encontrar
	public int getIndexVariavel(Token t, int escopo) {
		int i = 0;
		for (TokenSemanticoVariavel v : variaveis) {
			// compara até achar a variavel buscada
			if (v.lexema == t.lexema && v.escopo == escopo) {
				// retorna o indice dela no arraylist
				return i;
			}
			i++;
		}
		return -1;
	}
        // salva o índice da ultima variável + 1 para adicionar o tipo posterior
        public void saveVarAnchorIndex(){
            var_anchor_index = getLastIndexVariavel();
        }
        // seta o tipo em todas as variáveis dos parâmetros
        public void setTipeUntilAnchor(){
            for (int i=var_anchor_index; i<variaveis.size(); i++){
                variaveis.get(i).tipo_var = var_current_type;
            }
        }
        // retorna o último índice da lista
        public int getLastIndexVariavel() {
		return variaveis.size();
	}
        // altera o tipo atual das variáveis
        public void setCurrentVarType(String lexema){
            var_current_type = TokenSemanticoVariavel.TipoVar.tipoVarByLexema(
                    lexema
            );
        }
        // retrocede o escopo
	public void retrocedeEscopo(){
            id--;
        }
	// altera entre estar declarando uma variavel e um parâmetro
        public void declaratingVar(boolean declarating_var){
            this.declarating_var = declarating_var;
        }
        // adiciona erros de semântica baseado no tipo e inf do token
        private void logErro(Token encontrado, ErroSemanticoTipo erro_s_tipo) {
            erros_semanticos.add("ERRO - linha: " + encontrado.linha + 
                    "; col_ini: " + encontrado.coluna_ini + 
                    "; col_fim: " + encontrado.coluna_fim + 
                    "; lexema: " + encontrado.lexema + 
                    "; tipo erro: "+ erro_s_tipo.erro);
        }
        public TokenSemanticoProcedimento lastProcDecl(){
            TokenSemanticoProcedimento last_proc = null;
            try {
                last_proc = procedimentos.get(
                    procedimentos.size()-1
                ); 
            } catch (Exception e) {
            }
            return last_proc;
        }
        // salva o índice do ultimo secparam
        public void saveParamAnchorIndex(){
            try {
                param_anchor_index = lastProcDecl().parametros.size();
            } catch (Exception e) {
            }
            
        }
        
        // seta o tipo em todas as variáveis dos parâmetros
        public void setParamTipeUntilAnchor(){
            for (int i=param_anchor_index; i<lastProcDecl().parametros.size(); i++){
                lastProcDecl().parametros.get(i).tipo_var = var_current_type;
            }
        }
        // procedure para remover variáveis locais
        public void removeVarLocal(){
            ArrayList<TokenSemanticoVariavel> array_aux = (
                    ArrayList<TokenSemanticoVariavel>
                ) variaveis.clone();
            for (TokenSemanticoVariavel ts: array_aux){
                if (ts.escopo>0){
                    variaveis.remove(ts);
                }
            }
        }
        // salva último lexema para uma futura atribuicao ou procedimento
        public void saveTokenAtribOuProc(Token t){
            lex_var_or_proc = t;
        }
        public void comecaAtribuicao(){
            var_curr = getVariavelChecando(lex_var_or_proc);
            gerar("", Comando.TipoComando.ARMZ, ""+var_curr.end_rel);
        }
        public void terminaAtribuicao(){
            var_curr = null;
        }
        public void comecaProcedure(){
            proc_curr = getProcedureChecando(lex_var_or_proc);
        }
        public void terminaProcedure(){
            this.verificaUsoVariaveisLocais();
            proc_curr = null;
        }
        // procura variavel sem acusar erro
        public TokenSemanticoVariavel getVariavel(Token t){
            TokenSemanticoVariavel ts_aux = null;
            for (TokenSemanticoVariavel ts: variaveis){
                if (ts.lexema.equals(t.lexema)){
                    ts_aux = ts;
                }
            }
            if (proc_curr != null){
                for (TokenSemanticoVariavel ts: proc_curr.parametros){
                    if (ts.lexema.equals(t.lexema)){
                        ts_aux = ts;
                    }
                }
            }
            return ts_aux;
        }
        // procura variavel logando o erro no caso de nao encontrar
        public TokenSemanticoVariavel getVariavelChecando(Token t){
            TokenSemanticoVariavel ts = getVariavel(t);
            if (ts==null){
                logErro(t, ErroSemanticoTipo.NDecl);
            }
            else{
               ts.usada=true;
            }
            return ts;
        }
        // procura procedimento sem acusar erro
        public TokenSemanticoProcedimento getProcedure(Token t){
            TokenSemanticoProcedimento ts_aux = null;
            for (TokenSemanticoProcedimento ts: procedimentos){
                if (ts.lexema.equals(t.lexema)){
                    ts_aux = ts;
                }
            }
            return ts_aux;
        }
        // procura procedimento logando o erro no caso de nao encontrar
        public TokenSemanticoProcedimento getProcedureChecando(Token t){
            TokenSemanticoProcedimento ts = getProcedure(t);
            if (ts==null){
                logErro(t, ErroSemanticoTipo.NDecl);
            }
            else{
               ts.usada=true;  
            }
            return ts;
        }
        // atualiza o termo para a verificacao de tipo pelo metodo verificaExpTipo
        public void setTermoExpTipo(Token t){
            geraFator(t);
            if (t.tipo == Token.Tipo.Identificador){
                var_current_type = getVariavel(t).tipo_var;
            }
            else{
                if (
                    t.tipo == Token.Tipo.Numero_Inteiro||
                    t.tipo == Token.Tipo.Numero_Real
                ){
                    var_current_type = TokenSemanticoVariavel.TipoVar.Inteiro;
                }else{
                    var_current_type = TokenSemanticoVariavel.TipoVar.Booleano;
                }
            }
        }
        // verifica o uso correto entre tipos de expressao 1+1 e nao 1 and 2
        public void verificaExpTipo(Token t){
            geraOperadorExpr(t);
            if (
                t.tipo == Token.Tipo.Operador_Divisao||
                t.tipo == Token.Tipo.Operador_Multiplicacao||
                t.tipo == Token.Tipo.Operador_Soma||
                t.tipo == Token.Tipo.Operador_Subtracao||
                t.tipo == Token.Tipo.Div
            ){
                if (
                    (var_current_type != 
                        TokenSemanticoVariavel.TipoVar.Inteiro)
                        && 
                    (var_current_type != 
                        TokenSemanticoVariavel.TipoVar.Real)
                ){
                    logErro(t, ErroSemanticoTipo.Tipo);
                }
            }else{
                if (var_current_type != 
                        TokenSemanticoVariavel.TipoVar.Booleano){
                    logErro(t, ErroSemanticoTipo.Tipo);
                }
            }
        }
        // verifica se alguma procedure global n foi utilizada e loga o erro
        public void verificaUsoProcedures(){
            for (TokenSemanticoProcedimento ts: procedimentos){
                if(!(ts.usada)){
                    logErro(ts, ErroSemanticoTipo.NUtil);
                }  
            }
        }
        // verifica se alguma variavel local n foi utilizada e loga o erro
        public void verificaUsoVariaveisLocais(){
            for (TokenSemanticoVariavel ts_aux: variaveis){
                if(ts_aux.escopo > 0){
                    if (!(ts_aux.usada)){
                        logErro(ts_aux, ErroSemanticoTipo.NUtil);
                    }
                }
            }
            if (proc_curr != null){
                for (TokenSemanticoVariavel ts_aux: proc_curr.parametros){
                    if (!(ts_aux.usada)){
                            logErro(ts_aux, ErroSemanticoTipo.NUtil);
                        }
                }
            }
        }
        // verifica se alguma variavel global n foi utilizada e loga o erro
        public void verificaUsoVariaveisGlobais(){
            for (TokenSemanticoVariavel ts_aux: variaveis){
                if(ts_aux.escopo == 0){
                    if (!(ts_aux.usada)){
                        logErro(ts_aux, ErroSemanticoTipo.NUtil);
                    }
                }   
            }
        }
        // metodo para gerar codigo adicionando a lista de comandos
        public void gerar(
                String rotulo,
                Comando.TipoComando tipo_comando,
                String param
        ){
            comandos.add(new Comando(rotulo, tipo_comando, param));
        }
        public void geraOperadorExpr(Token t){
            switch (t.tipo){
                case And:
                    gerar("", Comando.TipoComando.CONJ, "");
                    break;
                case Or:
                    gerar("", Comando.TipoComando.DISJ, "");
                    break;
                case Not:
                    gerar("", Comando.TipoComando.NEGA, "");
                    break;
                case Div:
                    gerar("", Comando.TipoComando.DIVI, "");
                    break;
                case Operador_Soma:
                    gerar("", Comando.TipoComando.SOMA, "");
                    break;
                case Operador_Subtracao:
                    gerar("", Comando.TipoComando.SUBT, "");
                    break;
                case Operador_Multiplicacao:
                    gerar("", Comando.TipoComando.MULT, "");
                    break;
                case Operador_Divisao:
                    gerar("", Comando.TipoComando.DIVI, "");
                    break;
                default:
                    break;
            }
        }
        public void geraFator(Token t){
            if (t.tipo == Token.Tipo.Identificador){
                try {
                    TokenSemanticoVariavel ts = getVariavel(t);
                    if (ts.tipo_var == TokenSemanticoVariavel.TipoVar.Booleano){
                        if (ts.valor == "true"){
                            gerar("", Comando.TipoComando.CRCT, "1");
                        }else{
                            gerar("", Comando.TipoComando.CRCT, "0");
                        }
                    }
                    else{
                        gerar("", Comando.TipoComando.CRVL, ""+ts.end_rel);
                    }
                } catch (Exception e) {
                }
                
                
            }
            else{
                gerar("", Comando.TipoComando.CRCT, t.lexema);
            }
        }
        public void geraRelacao(Token t){
            switch(t.tipo){
                case Maior:
                    gerar("", Comando.TipoComando.CMMA, "");
                    break;
                case Menor:
                    gerar("", Comando.TipoComando.CMME, "");
                    break;
                case Igual:
                    gerar("", Comando.TipoComando.CMIG, "");
                    break;
                default:
                    break;
            }
        }
        public String geraRotulo(){
            rotulos++;
            return ("l "+rotulos);
        }
        
        
}
