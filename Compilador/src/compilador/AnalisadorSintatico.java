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
    private Token t;

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
        status = program();
        
        if(status){
            System.out.println("Sucesso");
        }
        
    }
    
    private boolean program(){
    	boolean bloco;
    	if(t.tipo == Token.Tipo.Programa){
    		getToken();
    		if(t.tipo == Token.Tipo.Identificador){
    			getToken();
    			if(t.tipo == Token.Tipo.Ponto_Virgula){
    				getToken();
    				if(bloco()){
    					getToken();
    					if(t.tipo == Token.Tipo.Composto_fim_codigo){
    						return true;
    					}else{
    						return false;
    					}
    				}else{
    					return false;
    				}
    					
    			}else{
    				return false;
    			}
    		}else{
    			return false;
    		}
    	}
    	else{
    		return false;
    	}
    }
    
    private boolean bloco(){
    	if(parteDeclVar()){
    		if(declProc()){
    			if(cmdComposto()){
    				return true;
    			}else{
    				return false;
    			}
    		}else{
    			return false;
    		}
    	}else{
    		return false;
    	}
    	
    }
    
    private boolean tipo(){
    	if(t.tipo == Token.Tipo.Identificador){
    		getToken();
    		return true;
    	}else{
    		return false;
    	}
    }

    private boolean parteDeclVar(){
    	if(declVar()){
    		if(declVarLoop()){
    			if(t.tipo == Token.Tipo.Ponto_Virgula){
    				getToken();
    				return true;
    			}else{
    				return false;
    			}
    		}else{
    			return false;
    		}
    	}else{
    		//vazio
    		return true;
    	}
    }
    
    private boolean declVarLoop(){
    	return true;
    }
    
    private boolean declVar(){
    	if(tipo()){
    		if(listaIdent()){
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return false;
    	}
    	
    }
    
    private boolean listaIdent(){
    	if(t.tipo == Token.Tipo.Identificador){
    		getToken();
    		if(identLoop()){
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return false;
    	}
    }
    
    private boolean identLoop(){
    	if(t.tipo == Token.Tipo.Virgula){
    		getToken();
    		if(t.tipo == Token.Tipo.Identificador){
    			getToken();
    			if(identLoop()){
    				return true;
    			}else{
    				return false;
    			}
    		}else{
    			return false;
    		}
    	}else{
    		//Vazio
    		return true;
    	}
    	
    }
    
    private boolean declProc(){
    	return true;
    }
    
    private boolean paramForm(){
    	return true;
    }
    
    private boolean secParamFormLoop(){
    	return true;
    }
    
    private boolean SecParamForm(){
    	return true;
    }
    
    private boolean cmdComposto(){
    	return true;
    }
    
    private boolean cmdLoop(){
    	return true;
    }
    
    private boolean comando(){
    	return true;
    }
    
    private boolean startIdent(){
    	return true;
    }
    
    private boolean optIdent(){
    	return true;
    }
    
    private boolean optListaExp(){
    	return true;
    }
    
    private boolean cmdCond(){
    	return true;
    }
    
    private boolean elseCmdOpt(){
    	return true;
    }
    
    private boolean cmdRep(){
    	return true;
    }
    
    private boolean expressao(){
    	return true;
    }
    
    private boolean relaOpt(){
    	return true;
    }
    
    private boolean relacao(){
    	return true;
    }
    
    private boolean expSimpl(){
    	return true;
    }
    
    private boolean sinalNOpt(){
    	return true;
    }
    
    private boolean sinalOpt(){
    	return true;
    }
    
    private boolean termoLoop(){
    	return true;
    }
    
    private boolean termo(){
    	return true;
    }
    
    private boolean fatorLoop(){
    	return true;
    }
    
    private boolean variavel(){
    	return true;
    }
    
    private boolean fator(){
    	return true;
    }
    
    private boolean exprOpt(){
    	return true;
    }
    
    private boolean listaExp(){
    	return true;
    }
    
    private boolean exprLoop(){
    	return true;
    }
    
    private void getToken(){
    	t = al.getToken();
    }
}
