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
    // CMD_COND -> if EXPRESSAO then COMANDO ELSE_CMD_OPT
    private boolean cmdCond(){
    	if (t.tipo == Token.Tipo.Condicional){
            getToken();
            if (expressao()){
                if (t.tipo == Token.Tipo.Condicionalt){
                    getToken();
                    if (comando()){
                        if (elseCmdOpt()){
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
    }
    // ELSE_CMD_OPT -> else COMANDO | ε
    private boolean elseCmdOpt(){
    	if (t.tipo == Token.Tipo.Condicionale){
            getToken();
            if (comando()){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
    // CMD_REP -> while EXPRESSAO do COMANDO
    private boolean cmdRep(){
    	if (t.tipo == Token.Tipo.Repeticao){
            getToken();
            if (expressao()){
                if (t.tipo == Token.Tipo.Repeticaod){
                    getToken();
                    if (comando()){
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
    // EXPRESSAO -> EXPR_SIMPL RELA_OPT
    private boolean expressao(){
    	if (expSimpl()){
            if (relaOpt()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // RELA_OPT -> RELACAO EXPR_SIMPL | ε
    private boolean relaOpt(){
    	if (relacao()){
            if (expSimpl()){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
    // RELACAO -> = | <> | < | <= | >= | >
    private boolean relacao(){
    	if (t.tipo == Token.Tipo.Maior){
            getToken();
            return true;
        }else if (t.tipo == Token.Tipo.Menor){
            getToken();
            return true;
        }else if (t.tipo == Token.Tipo.Igual){
            getToken();
            return true;
        }else{
            return false;
        }
    }
    // EXPR_SIMPL -> SINAL_OPT TERMO TERMO_LOOP
    private boolean expSimpl(){
    	if (sinalOpt()){
            if (termo()){
                if (termoLoop()){
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
    // SINAL_N_OPT ->  + | - 
    private boolean sinalNOpt(){
        if (t.tipo == Token.Tipo.Operador_Soma){
            getToken();
            return true;
        }else if (t.tipo == Token.Tipo.Operador_Subtracao){
            getToken();
            return true;
        }else{
            return false;
        }
    	
    }

    // SINAL_OPT ->  SINAL_N_OPT | ε
    private boolean sinalOpt(){
        if (sinalNOpt()){
            return true;
        }else{
            return false;
        }
    	
    }
    // TERMO_LOOP -> SINAL_N_OPT TERMO TERMO_LOOP | or TERMO TERMO_LOOP | ε
    private boolean termoLoop(){
        if (sinalNOpt()){
            if (termo()){
                if (termoLoop()){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else if (t.tipo == Token.Tipo.Or){
            if (termo()){
                if (termoLoop()){
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
    // TERMO -> FATOR FATOR_LOOP
    private boolean termo(){
    	if (fator()){
            if (fatorLoop()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // FATOR_LOOP -> * FATOR FATOR_LOOP | div FATOR FATOR_LOOP | 
    // and FATOR FATOR_LOOP | ε
    private boolean fatorLoop(){
        if(t.tipo == Token.Tipo.Operador_Multiplicacao){
            getToken();
            if (fator()){
                if (fatorLoop()){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else if(t.tipo == Token.Tipo.Operador_Divisao){
            getToken();
            if (fator()){
                if (fatorLoop()){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else if(t.tipo == Token.Tipo.And){
            getToken();
            if (fator()){
                if (fatorLoop()){
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
    // VARIAVEL -> identificador EXPR_OPT
    private boolean variavel(){
        if (t.tipo == Token.Tipo.Identificador){
            getToken();
            if (exprOpt()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // FATOR -> VARIAVEL | numero | ( EXPRESSAO ) | not FATOR
    private boolean fator(){
        if (variavel()){
            return true;
        }
        else if(t.tipo == Token.Tipo.Numero_Real || 
                t.tipo == Token.Tipo.Numero_Inteiro
                ){
            getToken();
            return true;
        }
        else if(t.tipo == Token.Tipo.Parenteses_Abre){
            getToken();
            if (expressao()){
                if(t.tipo == Token.Tipo.Parenteses_Fecha){
                    getToken();
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else if(t.tipo == Token.Tipo.Not){
            getToken();
            if (fator()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // EXPR_OPT -> EXPRESSAO | ε
    private boolean exprOpt(){
    	if(expressao()){
            return true;
        }else{
            return true;
        }
    }
    // LISTA_EXPR -> EXPRESSAO EXPR_LOOP
    private boolean listaExp(){
        if(expressao()){
            if(exprLoop()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // EXPR_LOOP -> , EXPRESSAO EXPR_LOOP | ε
    private boolean exprLoop(){
        if (t.tipo == Token.Tipo.Virgula){
            getToken();
            if(expressao()){
                if(exprLoop()){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        
        }else{
            // Vazio
            return true;
        }
    	
    }
    
    private void getToken(){
    	t = al.getToken();
    }
}
