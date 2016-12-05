/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.List;

/**
 *
 * @author gabrielozaki
 */
public class AnalisadorSintatico {

    private AnalisadorLexico al = new AnalisadorLexico();

    private enum Gramatica{
        //BNF Utilizada:
        //http://www2.fct.unesp.br/docentes/dmec/olivete/compiladores/arquivos/LALG.pdf
        //O nome do enum sera a o nome da regra e seu parametro a regra em si
        //Os tipos não terminais estarào identificados com < >, para facilitar a implementacao
        //Ex:
        //<programa> := program <identificador>;<bloco>.
        programa("program <identificador>;<bloco>."),
        //Em alguns pontos será necessário adaptar a BNF
        //O do bloco possui trechos opcionais
        //então temos:
        //A = BCD|BD|CD
        bloco("<parte_declara_variaveis><declara_sub><comando_composto>|"
                + "<parte_declara_variaveis><comando_composto>|"
                + "<declara_sub><comando_composto>|"
                + "<comando_composto>"),
        tipo("<identificador>"),
        //Aqui temos um caso de recursao do tipo:
        //A = aA|a
        parte_declara_variaveis(" var <declara_variaveis>;<parte_declara_variaveis>|var <declara_variaveis>;");
        
        
        public final String regra;
        
        //E considerada uma boa pratica de programacao proteger o construtor do enum
        //Garantindo assim tipos estaticos
        private Gramatica(String regra){
            this.regra = regra;
        }
    }
    
    public static void AnalisadorSintatico() {
        //so cria o construtor
    }

    public void populaLexico(String codigoFonte) {
        al.tokeniza(codigoFonte);
    }

    public List<Token> apenasLexico() {
        return al.getListaToken();
    }

    public void getGramatica() {
        Token t = al.getToken();;
        while (t != null) {
            System.out.println(t.lexema);
            t = al.getToken();
        }
    }
}
