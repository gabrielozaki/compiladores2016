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

    public static void AnalisadorSintatico() {
        //so cria o construtor
    }

    public void populaLexico(String codigoFonte) {
        al.tokeniza(codigoFonte);
    }

    public List<Token> apenasLexico() {
        return al.getListaToken();
    }

    public void executaAnalise() {
        boolean status;
        Token t = al.getToken();;
        /*while (t != null) {
            System.out.println(t.lexema);
            t = al.getToken();
        }*/
        status = programa(t);
        
        if(status){
            System.out.println("Sucesso");
        }
        
    }
    
    private boolean programa(Token t){
        boolean prog,ident,pontvirgula,bloc;
        
        if(t.tipo == Token.Tipo.Programa){
            prog = true;
            ident = identificador(al.getToken());
            t = al.getToken();
            pontvirgula = (t.tipo == Token.Tipo.Ponto_Virgula);
            bloc = bloco(al.getToken());
            return (prog && ident && pontvirgula && bloc);
        }
        return false;
    }
    
    private boolean bloco(Token t){
        System.out.println(t.lexema);
        return true;
    }
    
    private boolean tipo(Token t){
        return identificador(t);
    }

    
    private boolean listaExpressoes(Token t){
        return true;
    }
    
    private boolean numero(Token t){
        return (t.tipo == Token.Tipo.Numero_Real || t.tipo == Token.Tipo.Numero_Inteiro);
    }
    
    private boolean identificador(Token t){
        return (t.tipo == Token.Tipo.Identificador);
    }
}
