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
    
    //procedure identificador PARAM_FORM ; BLOCO ; | ε
    private boolean declProc(){
    	if(t.tipo == Token.Tipo.Procedure){
    		getToken();
    		if(t.tipo == Token.Tipo.Identificador){
    			getToken();
    			if(paramForm()){
    				if(t.tipo == Token.Tipo.Ponto_Virgula){
    					getToken();
    					if(bloco()){
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
    					return false;
    				}
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
    
    //(SEC_PARAM_FORM SEC_PARAM_FORM_LOOP ) | ε
    private boolean paramForm(){
    	if(secParamForm()){
    		if(secParamFormLoop()){
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return true;
    	}
    }
    
    //; SEC_PARAM_FORM SEC_PARAM_FORM_LOOP | ε
    private boolean secParamFormLoop(){
    	if(t.tipo == Token.Tipo.Ponto_Virgula){
    		getToken();
    		if(secParamForm()){
    			if(secParamFormLoop()){
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
    
    //var LISTA_IDENT : identificador | LISTA_IDENT : identificador
    private boolean secParamForm(){
    	if(t.tipo == Token.Tipo.Variavel){
    		getToken();
    		if(listaIdent()){
    			if(t.tipo == Token.Tipo.Dois_Pontos){
    				getToken();
    				if(t.tipo == Token.Tipo.Identificador){
    					getToken();
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
    	}else if(listaIdent()){
    		if(t.tipo == Token.Tipo.Dois_Pontos){
    			getToken();
    			if(t.tipo == Token.Tipo.Identificador){
    				getToken();
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
    
    //begin COMANDO CMD_LOOP end
    private boolean cmdComposto(){
    	if(t.tipo == Token.Tipo.Composto_inicio){
    		getToken();
    		if(comando()){
    			if(cmdLoop()){
    				if(t.tipo == Token.Tipo.Composto_fim){
    					getToken();
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
    
    //; COMANDO CMD_LOOP | ε
    private boolean cmdLoop(){
    	if(t.tipo == Token.Tipo.Ponto_Virgula){
    		getToken();
    		if(comando()){
    			if(cmdLoop()){
    				return true;
    			}else{
    				return false;
    			}
    		}else{
    			return false;
    		}
    	}else{
    		return true;
    	}
    	
    }
    
    //START_IDENT | CMD_COMPOSTO | CMD_COND | CMD_REP
    private boolean comando(){
    	if(startIdent()){
    		return true;
    	}else if(cmdComposto()){
    		return true;
    	}else if(cmdCond()){
    		return true;
    	}else if(cmdRep()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    //identificador OPT_IDENT
    private boolean startIdent(){
    	if(t.tipo == Token.Tipo.Identificador){
    		getToken();
    		if(optIdent()){
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return false;
    	}
    }
    
    //:= EXPRESSAO | OPT_LISTA_EXPR | EXPR_OPT
    private boolean optIdent(){
    	if(t.tipo == Token.Tipo.Atribuicao){
    		getToken();
    		if(expressao()){
    			return true;
    		}else{
    			return false;
    		}
    	}else if(optListaExp()){
    		return true;
    	}else if(exprOpt()){
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    //( LISTA_EXPR ) | ε
    private boolean optListaExp(){
    	if(t.tipo == Token.Tipo.Parenteses_Abre){
    		getToken();
    		if(listaExp()){
    			if(t.tipo == Token.Tipo.Parenteses_Fecha){
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
