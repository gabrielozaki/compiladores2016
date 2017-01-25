/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabrielozaki
 */
public class AnalisadorSintatico {

    private AnalisadorLexico al = new AnalisadorLexico();
    private Token t;
    private List<String> erro = new ArrayList<String>();

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
        getToken();
        /*while (t != null) {
            System.out.println(t.lexema);
            t = al.getToken();
        }*/
        status = program();

        for (String e : erro) {
            System.out.println(e);
        }

        if (status) {
            System.out.println("Sucesso");
        } else {
            System.out.println("Falha");
        }

    }

    private boolean program() {
        //  System.out.println("program");

        if (t.tipo != Token.Tipo.Programa) {

            if (!modoPanico(Token.Tipo.Programa)) {
                return false;
            }
            //           return false;
        }

        getToken();

        if (t.tipo != Token.Tipo.Identificador
                && t.tipo != Token.Tipo.Inteiro
                && t.tipo != Token.Tipo.Real
                && t.tipo != Token.Tipo.Valor_Boleano
                && t.tipo != Token.Tipo.Leitura
                && t.tipo != Token.Tipo.Escrita
                && t.tipo != Token.Tipo.Booleano) {
            if (!modoPanico(Token.Tipo.Identificador)) {
                return false;
            }
        }

        getToken();

        if (t.tipo != Token.Tipo.Ponto_Virgula) {
            if (!modoPanico(Token.Tipo.Ponto_Virgula)) {
                return false;
            }
        }

        getToken();

        if (bloco()) {

            if (t.tipo != Token.Tipo.Composto_fim_codigo) {
                if (!modoPanico(Token.Tipo.Composto_fim_codigo)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean bloco() {
        //  System.out.println("bloco");
        if (parteDeclVar()) {
            if (declProc()) {
                if (cmdComposto()) {
                    return true;
                } else {
                    //caso especial, como o cmdcomposto não pode processar o fim de codigo e sua recursividade o força a tentar
                    //Fazemos essa verificação para que ele chegue ao final da execução
                    if (t.tipo == Token.Tipo.Composto_fim_codigo) {
                        return true;
                    }
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    private boolean tipo() {
        //System.out.println("tipo");
        if (t.tipo == Token.Tipo.Identificador
                || t.tipo == Token.Tipo.Inteiro
                || t.tipo == Token.Tipo.Real
                || t.tipo == Token.Tipo.Valor_Boleano
                || t.tipo == Token.Tipo.Leitura
                || t.tipo == Token.Tipo.Escrita
                || t.tipo == Token.Tipo.Booleano) {
            getToken();
            return true;
        } else {
            logErro(t, Token.Tipo.Identificador.name());

            return false;
        }
    }

    private boolean parteDeclVar() {
        //System.out.println("partDeclVar");
        if (declVar()) {
            if (t.tipo == Token.Tipo.Ponto_Virgula) {
                getToken();
                if (parteDeclVar()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                logErro(t, Token.Tipo.Ponto_Virgula.name());
                return false;
            }
        } else {
            //vazio
            removeErro();
            return true;

        }
    }

    private boolean declVar() {
        //System.out.println("declVar");
        if (tipo()) {
            if (listaIdent()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    private boolean listaIdent() {
        //System.out.println("listaIdent");
        if (t.tipo == Token.Tipo.Identificador
                || t.tipo == Token.Tipo.Inteiro
                || t.tipo == Token.Tipo.Real
                || t.tipo == Token.Tipo.Valor_Boleano
                || t.tipo == Token.Tipo.Leitura
                || t.tipo == Token.Tipo.Escrita
                || t.tipo == Token.Tipo.Booleano) {
            getToken();
            if (identLoop()) {
                return true;
            } else {
                return false;
            }
        } else {
            logErro(t, Token.Tipo.Identificador.name());
            return false;
        }
    }

    private boolean identLoop() {
        //System.out.println("identLoop");
        if (t.tipo == Token.Tipo.Virgula) {
            getToken();
            if (t.tipo == Token.Tipo.Identificador
                    || t.tipo == Token.Tipo.Inteiro
                    || t.tipo == Token.Tipo.Real
                    || t.tipo == Token.Tipo.Valor_Boleano
                    || t.tipo == Token.Tipo.Leitura
                    || t.tipo == Token.Tipo.Escrita
                    || t.tipo == Token.Tipo.Booleano) {
                getToken();
                if (identLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                logErro(t, Token.Tipo.Identificador.name());
                return false;
            }
        } else {
            //Vazio
            removeErro();
            return true;
        }

    }

    //procedure identificador PARAM_FORM ; BLOCO ; | ε
    private boolean declProc() {
        //System.out.println("declProc");
        if (t.tipo == Token.Tipo.Procedure) {
            getToken();
            if (t.tipo == Token.Tipo.Identificador
                    || t.tipo == Token.Tipo.Inteiro
                    || t.tipo == Token.Tipo.Real
                    || t.tipo == Token.Tipo.Valor_Boleano
                    || t.tipo == Token.Tipo.Leitura
                    || t.tipo == Token.Tipo.Escrita
                    || t.tipo == Token.Tipo.Booleano) {
                getToken();
                if (paramForm()) {
                    if (t.tipo == Token.Tipo.Ponto_Virgula) {
                        getToken();
                        if (bloco()) {
                            if (t.tipo == Token.Tipo.Ponto_Virgula) {
                                getToken();
                                return true;
                            } else {
                                logErro(t, Token.Tipo.Ponto_Virgula.name());
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        logErro(t, Token.Tipo.Ponto_Virgula.name());
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                logErro(t, Token.Tipo.Identificador.name());
                return false;
            }
        } else {
            //vazio
            removeErro();
            return true;
        }
    }

    //(SEC_PARAM_FORM SEC_PARAM_FORM_LOOP ) | ε
    private boolean paramForm() {
        //System.out.println("paramForm");
        if (t.tipo == Token.Tipo.Parenteses_Abre) {
            getToken();
            if (secParamForm()) {
                if (secParamFormLoop()) {
                    if (t.tipo == Token.Tipo.Parenteses_Fecha) {
                        getToken();
                        return true;
                    } else {
                        logErro(t, Token.Tipo.Parenteses_Fecha.name());
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            logErro(t, Token.Tipo.Parenteses_Abre.name());
            return true;
        }
    }

    //; SEC_PARAM_FORM SEC_PARAM_FORM_LOOP | ε
    private boolean secParamFormLoop() {
        // System.out.println("secParamFormLoop");
        if (t.tipo == Token.Tipo.Ponto_Virgula) {
            getToken();
            if (secParamForm()) {
                if (secParamFormLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            //vazio
            removeErro();
            return true;
        }

    }

    //var LISTA_IDENT : identificador | LISTA_IDENT : identificador
    private boolean secParamForm() {
        //System.out.println("secParamForm");
        if (t.tipo == Token.Tipo.Variavel) {
            getToken();
            if (listaIdent()) {
                if (t.tipo == Token.Tipo.Dois_Pontos) {
                    getToken();
                    if (t.tipo == Token.Tipo.Identificador
                            || t.tipo == Token.Tipo.Inteiro
                            || t.tipo == Token.Tipo.Real
                            || t.tipo == Token.Tipo.Valor_Boleano
                            || t.tipo == Token.Tipo.Leitura
                            || t.tipo == Token.Tipo.Escrita
                            || t.tipo == Token.Tipo.Booleano) {
                        getToken();
                        return true;
                    } else {
                        logErro(t, Token.Tipo.Identificador.name());
                        return false;
                    }
                } else {
                    logErro(t, Token.Tipo.Dois_Pontos.name());
                    return false;
                }
            } else {
                return false;
            }
        } else if (listaIdent()) {
            if (t.tipo == Token.Tipo.Dois_Pontos) {
                getToken();
                if (t.tipo == Token.Tipo.Identificador
                        || t.tipo == Token.Tipo.Inteiro
                        || t.tipo == Token.Tipo.Real
                        || t.tipo == Token.Tipo.Valor_Boleano
                        || t.tipo == Token.Tipo.Leitura
                        || t.tipo == Token.Tipo.Escrita
                        || t.tipo == Token.Tipo.Booleano) {
                    getToken();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    //begin COMANDO CMD_LOOP end
    private boolean cmdComposto() {
        //System.out.println("cmdComposto");
        if (t.tipo == Token.Tipo.Composto_inicio) {
            getToken();
            if (comando()) {
                if (cmdLoop()) {
                    if (t.tipo == Token.Tipo.Composto_fim) {
                        getToken();
                        return true;
                    } else {
                        if (t.tipo != Token.Tipo.Composto_fim_codigo) {
                            logErro(t, Token.Tipo.Composto_fim.name());
                        }
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            logErro(t, Token.Tipo.Composto_inicio.name());
            return false;
        }
    }

    //; COMANDO CMD_LOOP | ε
    private boolean cmdLoop() {
        //System.out.println("cmdLoop");
        if (t.tipo == Token.Tipo.Ponto_Virgula) {
            getToken();
            if (comando()) {
                if (cmdLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            //vazio
            removeErro();
            return true;
        }

    }

    //START_IDENT | CMD_COMPOSTO | CMD_COND | CMD_REP
    private boolean comando() {
        //System.out.println("comando");
        if (startIdent()) {
            return true;
        } else if (cmdComposto()) {
            return true;
        } else if (cmdCond()) {
            return true;
        } else if (cmdRep()) {
            return true;
        } else {
            return false;
        }
    }

    //identificador OPT_IDENT
    private boolean startIdent() {
        //System.out.println("startIdent");
        if (t.tipo == Token.Tipo.Identificador
                || t.tipo == Token.Tipo.Inteiro
                || t.tipo == Token.Tipo.Real
                || t.tipo == Token.Tipo.Valor_Boleano
                || t.tipo == Token.Tipo.Leitura
                || t.tipo == Token.Tipo.Escrita
                || t.tipo == Token.Tipo.Booleano) {
            getToken();
            if (optIdent()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //:= EXPRESSAO | OPT_LISTA_EXPR | EXPR_OPT
    private boolean optIdent() {
        //System.out.println("optIdent");
        if (t.tipo == Token.Tipo.Atribuicao) {
            getToken();
            if (expressao()) {
                return true;
            } else {
                return false;
            }
        } else if (optListaExp()) {
            return true;
        } else if (exprOpt()) {
            return true;
        } else {
            return false;
        }

    }

    //( LISTA_EXPR ) | ε
    private boolean optListaExp() {
        //System.out.println("optListaExp");
        if (t.tipo == Token.Tipo.Parenteses_Abre) {
            getToken();
            if (listaExp()) {
                if (t.tipo == Token.Tipo.Parenteses_Fecha) {
                    getToken();
                    return true;
                } else {
                    logErro(t, Token.Tipo.Parenteses_Fecha.name());
                    return false;
                }
            } else {
                return false;
            }
        } else {
            //vazio
            removeErro();
            return true;
        }
    }

    // CMD_COND -> if EXPRESSAO then COMANDO ELSE_CMD_OPT
    private boolean cmdCond() {
        //System.out.println("cmdCond");
        if (t.tipo == Token.Tipo.Condicional) {
            getToken();
            if (expressao()) {
                if (t.tipo == Token.Tipo.Condicionalt) {
                    getToken();
                    if (comando()) {
                        if (elseCmdOpt()) {
                            return true;

                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    logErro(t, Token.Tipo.Condicionalt.name());
                    return false;
                }
            } else {
                return false;
            }
        } else {
            logErro(t, Token.Tipo.Condicional.name());
            return false;
        }
    }

    // ELSE_CMD_OPT -> else COMANDO | ε
    private boolean elseCmdOpt() {
        //System.out.println("elseCmdOpt");
        if (t.tipo == Token.Tipo.Condicionale) {
            getToken();
            if (comando()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    // CMD_REP -> while EXPRESSAO do COMANDO
    private boolean cmdRep() {
        //System.out.println("cmdRep");
        if (t.tipo == Token.Tipo.Repeticao) {
            getToken();
            if (expressao()) {
                if (t.tipo == Token.Tipo.Repeticaod) {
                    getToken();
                    if (comando()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    logErro(t, Token.Tipo.Repeticaod.name());
                    return false;
                }
            } else {
                return false;
            }
        } else {
            logErro(t, Token.Tipo.Repeticao.name());
            return false;
        }
    }

    // EXPRESSAO -> EXPR_SIMPL RELA_OPT
    private boolean expressao() {
        //System.out.println("expressao");
        if (expSimpl()) {
            if (relaOpt()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // RELA_OPT -> RELACAO EXPR_SIMPL | ε
    private boolean relaOpt() {
       // System.out.println("relaOpt");
        if (relacao()) {
            if (expSimpl()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    // RELACAO -> = | <> | < | <= | >= | >
    private boolean relacao() {
        //System.out.println("relacao");
        if (t.tipo == Token.Tipo.Maior) {
            getToken();
            return true;
        } else if (t.tipo == Token.Tipo.Menor) {
            getToken();
            return true;
        } else if (t.tipo == Token.Tipo.Igual) {
            getToken();
            return true;
        } else {
            return false;
        }
    }

    // EXPR_SIMPL -> SINAL_OPT TERMO TERMO_LOOP
    private boolean expSimpl() {
        //System.out.println("exprSimpl");
        if (sinalOpt()) {
            if (termo()) {
                if (termoLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // SINAL_N_OPT ->  + | - 
    private boolean sinalNOpt() {
        //System.out.println("sinalNOpt");
        if (t.tipo == Token.Tipo.Operador_Soma) {
            getToken();
            return true;
        } else if (t.tipo == Token.Tipo.Operador_Subtracao) {
            getToken();
            return true;
        } else {
            return false;
        }

    }

    // SINAL_OPT ->  SINAL_N_OPT | ε
    private boolean sinalOpt() {
        //System.out.println("sinalOpt");
        if (sinalNOpt()) {
            return true;
        } else {
            // vazio
            removeErro();
            return true;
        }

    }

    // TERMO_LOOP -> SINAL_N_OPT TERMO TERMO_LOOP | or TERMO TERMO_LOOP | ε
    private boolean termoLoop() {
        //System.out.println("termoLoop");
        if (sinalNOpt()) {
            if (termo()) {
                if (termoLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (t.tipo == Token.Tipo.Or) {
            if (termo()) {
                if (termoLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            //vario
            removeErro();
            return true;
        }
    }

    // TERMO -> FATOR FATOR_LOOP
    private boolean termo() {
        //System.out.println("termo");
        if (fator()) {
            if (fatorLoop()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // FATOR_LOOP -> * FATOR FATOR_LOOP | div FATOR FATOR_LOOP | 
    // and FATOR FATOR_LOOP | ε
    private boolean fatorLoop() {
        //System.out.println("fatorLoop");
        if (t.tipo == Token.Tipo.Operador_Multiplicacao) {
            getToken();
            if (fator()) {
                if (fatorLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (t.tipo == Token.Tipo.Div) {
            getToken();
            if (fator()) {
                if (fatorLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (t.tipo == Token.Tipo.And) {
            getToken();
            if (fator()) {
                if (fatorLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }

    }

    // VARIAVEL -> identificador EXPR_OPT
    private boolean variavel() {
        //System.out.println("variavel");
        if (t.tipo == Token.Tipo.Identificador
                || t.tipo == Token.Tipo.Inteiro
                || t.tipo == Token.Tipo.Real
                || t.tipo == Token.Tipo.Valor_Boleano
                || t.tipo == Token.Tipo.Leitura
                || t.tipo == Token.Tipo.Escrita
                || t.tipo == Token.Tipo.Booleano) {
            getToken();
            if (exprOpt()) {
                return true;
            } else {
                return false;
            }
        } else {
            logErro(t, Token.Tipo.Identificador.name());
            return false;
        }
    }

    // FATOR -> VARIAVEL | numero | ( EXPRESSAO ) | not FATOR
    private boolean fator() {
        //System.out.println("fator");
        if (variavel()) {
            return true;
        } else if (t.tipo == Token.Tipo.Numero_Real
                || t.tipo == Token.Tipo.Numero_Inteiro) {
            getToken();
            return true;
        } else if (t.tipo == Token.Tipo.Parenteses_Abre) {
            getToken();
            if (expressao()) {
                if (t.tipo == Token.Tipo.Parenteses_Fecha) {
                    getToken();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (t.tipo == Token.Tipo.Not) {
            getToken();
            if (fator()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // EXPR_OPT -> EXPRESSAO | ε
    private boolean exprOpt() {
       // System.out.println("exprOpt");
        if (expressao()) {
            return true;
        } else {
            removeErro();
            return true;
        }
    }

    // LISTA_EXPR -> EXPRESSAO EXPR_LOOP
    private boolean listaExp() {
       // System.out.println("listaExp");
        if (expressao()) {
            if (exprLoop()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // EXPR_LOOP -> , EXPRESSAO EXPR_LOOP | ε
    private boolean exprLoop() {
       // System.out.println("exprLoop");
        if (t.tipo == Token.Tipo.Virgula) {
            getToken();
            if (expressao()) {
                if (exprLoop()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } else {
            // Vazio
            removeErro();
            return true;
        }

    }

    private void getToken() {
        t = al.getToken();
        /* if (t != null) {
            System.out.println("LEXEMA: " + t.lexema);
            System.out.println("LEXEMA: " + t.tipo);
        }*/
    }

    private boolean modoPanico(Token.Tipo tipo) {
        if (t != null) {
            logErro(t, tipo.name());
        }
        //System.out.println("Entrando em modo de panico");
        //System.out.println("Era esperado um token do tipo" + tipo);
        while (t != null && t.tipo != tipo) {
            getToken();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnalisadorSintatico.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (t == null) {
                return false;
            }
        }

        // System.out.println("Saindo do modo de panico");
        return true;
    }

    private void logErro(Token encontrado, String esperado) {
        erro.add("ERRO:" + encontrado.linha + ":" + encontrado.coluna_ini + ":" + encontrado.coluna_fim + " " + encontrado.lexema + " inesperado, era esperado um '" + esperado + "'");
    }

    private void removeErro() {

        if (erro.size() >= 1) {
         //   System.out.println("REMOVE ERRO");
            erro.remove(erro.size() - 1);
        }
    }
}
