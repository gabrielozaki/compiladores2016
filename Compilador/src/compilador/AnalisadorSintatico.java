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
        status = program(t);
        
        if(status){
            System.out.println("Sucesso");
        }
        
    }
    
    private boolean program(Token t){
    	return true;
    }
    
    private boolean bloco(Token t){
    	return true;
    }
    
    private boolean tipo(Token t){
    	return true;
    }

    private boolean parteDeclVar(Token t){
    	return true;
    }
    
    private boolean declVarLoop(Token t){
    	return true;
    }
    
    private boolean declVar(Token t){
    	return true;
    }
    
    private boolean listaIdent(Token t){
    	return true;
    }
    
    private boolean identLoop(Token t){
    	return true;
    }
    
    private boolean declProc(Token t){
    	return true;
    }
    
    private boolean paramForm(Token t){
    	return true;
    }
    
    private boolean secParamFormLoop(Token t){
    	return true;
    }
    
    private boolean SecParamForm(Token t){
    	return true;
    }
    
    private boolean CmdComposto(Token t){
    	return true;
    }
    
    private boolean cmdLoop(Token t){
    	return true;
    }
    
    private boolean comando(Token t){
    	return true;
    }
    
    private boolean startIdent(Token t){
    	return true;
    }
    
    private boolean optIdent(Token t){
    	return true;
    }
    
    private boolean optListaExp(Token t){
    	return true;
    }
    
    private boolean cmdCond(Token t){
    	return true;
    }
    
    private boolean elseCmdOpt(Token t){
    	return true;
    }
    
    private boolean cmdRep(Token t){
    	return true;
    }
    
    private boolean expressao(Token t){
    	return true;
    }
    
    private boolean relaOpt(Token t){
    	return true;
    }
    
    private boolean relacao(Token t){
    	return true;
    }
    
    private boolean expSimpl(Token t){
    	return true;
    }
    
    private boolean sinalNOpt(Token t){
    	return true;
    }
    
    private boolean sinalOpt(Token t){
    	return true;
    }
    
    private boolean termoLoop(Token t){
    	return true;
    }
    
    private boolean termo(Token t){
    	return true;
    }
    
    private boolean fatorLoop(Token t){
    	return true;
    }
    
    private boolean variavel(Token t){
    	return true;
    }
    
    private boolean fator(Token t){
    	return true;
    }
    
    private boolean exprOpt(Token t){
    	return true;
    }
    
    private boolean listaExp(Token t){
    	return true;
    }
    
    private boolean exprLoop(Token t){
    	return true;
    }
    
    
}
