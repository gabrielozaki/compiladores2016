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
	// Lista de Tokens de variaveis e de procedimentos, basicamente seria as
	// nossas tabelas
	// São separadas para acelerar e facilitar as buscas
	ArrayList<TokenSemanticoProcedimento> procedimentos = new ArrayList<>();
	ArrayList<TokenSemanticoVariavel> variaveis = new ArrayList<>();
        ArrayList<String> erros_semanticos = new ArrayList<>();

	public AnalisadorSemantico() {
		// Construtor vazio
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
            System.out.println("existeEEEEEE");
            System.out.print(escopo);
            System.out.print(t.lexema);
            for (int i=var_anchor_index; i<var_array.size(); i++){
                TokenSemanticoVariavel v = var_array.get(i);
                if (v.lexema.equals(t.lexema) && (v.escopo == escopo)) {
                    return true;
                }
            }/*
            for (TokenSemanticoVariavel v : variaveis) {
                    // Verifica se a variavel já existe naquele escopo
                    System.out.println("V: "+v.lexema);
                    System.out.println(v.escopo);
                    if (v.lexema.equals(t.lexema) && (v.escopo == escopo)) {
                            return true;
                    }
            }*/
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
        //procedure proc(var a1, a2, a1 : int; var a2 : boolean );
}
