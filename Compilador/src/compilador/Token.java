/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;
/**
 *
 * @author gabrielozaki
 * @author guilherme
 */
public class Token {
    
    //Os tipos sao enumeraveis, para facilitar sua utilizacao
    public static enum Tipo{
        //O tipo de token recebe como parametro o seu regex
        
        //BNF Utilizada:
        //http://www2.fct.unesp.br/docentes/dmec/olivete/compiladores/arquivos/LALG.pdf
        //Palavras reservadas(ex:comandos), como os testes ocorrem em ordem do tipo, entao inserimos as reservadas primeiro
        //Declaracao de programa 1
        Programa("program"),
        //Declaracao de variavel 4
        Variavel("var"),
        //Declaracao de procedimento 8
        Procedure("procedure"),
        //Declaracao de tipos OBS da pag 1
        //NOTA isso e diferente de Numero_Inteiro, um e a declaracao e o outro e o numero em si
        //Nos codigos de exemplo o professor usou int ao inves de integer, logo aceitaremos ambos
        Inteiro("integer|int"),
        Real("real"),
        Booleano("boolean"),
        //Valores que um booleano pode assumir, uso isolado tambem e comum
        Valor_Boleano("true|false"),
        //Declaracao de comandos de leitura escrita OBS da pag 1
        Leitura("read"),
        Escrita("write"),
        //Declaracao de comando composto 11
        Composto_inicio("begin"),
        Composto_fim("end"),
        //Declaracao de condicao 15
        Condicional("if"),
        Condicionalt("then"),
        Condicionale("else"),
        //Declaracao do repeticao 16
        Repeticao("while"),
        Repeticaod("do"),
        //Declaracao de relacoes 18
        //NOTA nao inserimos <> <= e >= porque essa analise composta cabe ao analisador sintatico
        //Ex: declarar que =< e errado
        Igual("="),
        Maior(">"),
        Menor("<"),
        
        //Operadores logicos 20 
        And("and"),
        Or("or"),
        Div("div"),
        Not("not"),
        
        //identificando simbolos
        Dois_Pontos(":"),
        Ponto_Virgula(";"),
        Virgula(","),
        
        //Comentarios
        Chaves_Abre("\\{"),
        Chaves_Fecha("\\}"),
        //Para Reais, esse regex exige que tenha pelo menos um numero antes do . e pelo menos um numero depois, logo .3 ou 2. nao sao validos
        Numero_Real("[0-9]+.[0-9]+"),
        //Para Inteiros, esse regex exige que tenha pelo menos um numero 
        Numero_Inteiro("[0-9]+"),
        
        //Operacoes
        Operador_Soma("[+]"),
        Operador_Subtracao("[-]"),
        Operador_Multiplicacao("[*]"),
        Operador_Divisao("[/]"),
        Parenteses_Abre("[(]"),
        Parenteses_Fecha("[)]"),
        
        //Declaramos os identificadores
        Identificador("(_|[a-z]|[A-Z])+(_|[a-z]|[A-Z]|[0-9])*");
        
        
        private final String regex;
        
        //E considerada uma boa pratica de programacao proteger o construtor do enum
        //Garantindo assim tipos estaticos
        private Tipo(String regex){
            this.regex = regex;
        }
    }
    
    //Os erros tambem sao enum
    public static enum Erro{
        Nao_Pertence_Alfabeto;
    }
    
    public String lexema  = "";
    public int linha      = -1;
    public int coluna_ini = -1;
    public int coluna_fim = -1;
    public Tipo tipo      = null;
    public Erro erro      = null;
    
    //O construtor requer um lexema, sua linha, sua coluna inicial e sua coluna final
    public Token(String lexema,int linha,int ini, int fim){
        this.lexema     = lexema;
        this.linha      = linha;
        this.coluna_ini = ini;
        this.coluna_fim = fim;
    }
    
    //Classicamos o token, por exemplo:
    //3.2 | Numero_Real
    // + | Operador_Soma
    //( |  Parenteses_Abre
    public void classificaToken(){
        //Verificador de erro, caso ele chegue true ao final significa que nenhum regex o classificou
        boolean erro = true;
        //Percorre todos os tipos de tokens
        for(Tipo t: Tipo.values())
        {
            //Se coincidir com algum, o classifica como sendo daquele tipo
            if(this.lexema.matches(t.regex)){
                this.tipo = t;
                //Evita o erro
                erro      = false;
                //Como ja encontrou o tipo, nao e necessario processar os outros tipos
                break;
            }
        }
        //Se chegou aqui, e porque o lexima nao pertence ao alfabeto
        if(erro)
            this.erro = Erro.Nao_Pertence_Alfabeto;
            
    }
    
    public void listaTipo(){
        for(Tipo t: Tipo.values())
        {
            System.out.println(t.name());
        }
    }
}
