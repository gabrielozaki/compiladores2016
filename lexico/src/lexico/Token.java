/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;
/**
 *
 * @author gabrielozaki
 * @author guilherme
 */
public class Token {
    
    //Os tipos sao enumeraveis, para facilitar sua utilizacao
    public static enum Tipo{
        //O tipo de token recebe como parametro o seu regex
        //Para Reais, esse regex exige que tenha pelo menos um numero antes do . e pelo menos um numero depois, logo .3 ou 2. nao sao validos
        Numero_Real("[0-9]+.[0-9]+"),
        //Para Inteiros, esse regex exige que tenha pelo menos um numero 
        Numero_Inteiro("[0-9]+"),
        //Resto das operacoes apenas verifica o caractere
        Operador_Soma("[+]"),
        Operador_Subtracao("[-]"),
        Operador_Multiplicacao("[*]"),
        Operador_Divisao("[/]"),
        Parenteses_Abre("[(]"),
        Parenteses_Fecha("[)]");
        
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
}
