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
    public List<String> erro = new ArrayList<String>();

    private List<Token.Tipo> followProgram = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followBloco = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followProgramPontoEVirgula = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followTipo = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followParteDeclVar = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followDeclVarLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followDeclVar = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followListaIdent = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followIdentLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followDeclProc = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followParamForm = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSecParamFormLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSecParamForm = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdComposto = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followComando = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followStartIdent = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followOptIdent = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followOptListaExpr = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdCond = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followElseCmdOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdRep = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExpressao = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followRelaOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followRelacao = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExprSimpl = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSinalNOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSinalOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followTermoLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followTermo = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followFatorLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followVariavel = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followFator = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExprOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followListaExpr = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExprLoop = new ArrayList<Token.Tipo>();
    
    
    // FIRSTs
    
    private List<Token.Tipo> firstProgram = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstBloco = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstIdentLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstParamForm = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstSecParamForm = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstComando = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstCmdLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstOptIdent = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> firstExpressao = new ArrayList<Token.Tipo>();

    /*private List<Token.Tipo> followProgramPontoEVirgula = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followTipo = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followParteDeclVar = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followDeclVarLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followDeclVar = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followListaIdent = new ArrayList<Token.Tipo>();
    
    private List<Token.Tipo> followDeclProc = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followParamForm = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSecParamFormLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSecParamForm = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdComposto = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followComando = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followStartIdent = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followOptIdent = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followOptListaExpr = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdCond = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followElseCmdOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followCmdRep = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExpressao = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followRelaOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followRelacao = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExprSimpl = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSinalNOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followSinalOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followTermoLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followTermo = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followFatorLoop = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followVariavel = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followFator = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExprOpt = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followListaExpr = new ArrayList<Token.Tipo>();
    private List<Token.Tipo> followExprLoop = new ArrayList<Token.Tipo>();*/

    public static void AnalisadorSintatico() {
        //so cria o construtor
        System.out.println("compilador.AnalisadorSintatico.AnalisadorSintatico()");
        
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
        program();

        for (String e : erro) {
            System.out.println(e);
        }
        /*
        if (status) {
            System.out.println("Sucesso");
        } else {
            System.out.println("Falha");
        }*/

    }

    private void iniciaFollows() {
        //programa
        followProgram.add(Token.Tipo.Programa);

        //Bloco
        followBloco.add(Token.Tipo.Composto_fim_codigo);
        followBloco.add(Token.Tipo.Ponto_Virgula);

        //Tipo
        followTipo.add(Token.Tipo.Identificador);

        //ParteDeclVar
        followParteDeclVar.add(Token.Tipo.Procedure);
        followParteDeclVar.add(Token.Tipo.Composto_inicio);

        //DeclVarLoop
        followDeclVarLoop.add(Token.Tipo.Ponto_Virgula);

        //DeclVar
        followDeclVar.add(Token.Tipo.Ponto_Virgula);

        //ListaIdent
        followListaIdent.add(Token.Tipo.Ponto_Virgula);
        followListaIdent.add(Token.Tipo.Dois_Pontos);

        //IdentLoop
        followIdentLoop.add(Token.Tipo.Ponto_Virgula);
        followIdentLoop.add(Token.Tipo.Dois_Pontos);

        //DeclProc
        followDeclProc.add(Token.Tipo.Composto_inicio);

        //ParamForm
        followParamForm.add(Token.Tipo.Ponto_Virgula);

        //SecParamFormLoop
        followSecParamFormLoop.add(Token.Tipo.Parenteses_Fecha);

        //SecParamForm
        followSecParamForm.add(Token.Tipo.Ponto_Virgula);
        followSecParamForm.add(Token.Tipo.Parenteses_Fecha);

        //CmdComposto
        followCmdComposto.add(Token.Tipo.Ponto_Virgula);
        followCmdComposto.add(Token.Tipo.Composto_fim);
        followCmdComposto.add(Token.Tipo.Condicional);

        followCmdLoop.add(Token.Tipo.Composto_fim);

        followComando.add(Token.Tipo.Ponto_Virgula);
        followComando.add(Token.Tipo.Composto_fim);
        followComando.add(Token.Tipo.Condicional);

        //StartIdent
        followStartIdent.add(Token.Tipo.Ponto_Virgula);
        followStartIdent.add(Token.Tipo.Composto_fim);
        followStartIdent.add(Token.Tipo.Condicional);

        followOptIdent.add(Token.Tipo.Ponto_Virgula);
        followOptIdent.add(Token.Tipo.Composto_fim);
        followOptIdent.add(Token.Tipo.Condicional);

        followOptListaExpr.add(Token.Tipo.Ponto_Virgula);
        followOptListaExpr.add(Token.Tipo.Composto_fim);
        followOptListaExpr.add(Token.Tipo.Condicional);

        followCmdCond.add(Token.Tipo.Ponto_Virgula);
        followCmdCond.add(Token.Tipo.Composto_fim);
        followCmdCond.add(Token.Tipo.Condicional);

        followElseCmdOpt.add(Token.Tipo.Ponto_Virgula);
        followElseCmdOpt.add(Token.Tipo.Composto_fim);
        followElseCmdOpt.add(Token.Tipo.Condicional);

        followCmdRep.add(Token.Tipo.Ponto_Virgula);
        followCmdRep.add(Token.Tipo.Composto_fim);
        followCmdRep.add(Token.Tipo.Condicional);

        followExpressao.add(Token.Tipo.Ponto_Virgula);
        followExpressao.add(Token.Tipo.Composto_fim);
        followExpressao.add(Token.Tipo.Condicional);
        followExpressao.add(Token.Tipo.Operador_Multiplicacao);
        followExpressao.add(Token.Tipo.Operador_Divisao);
        followExpressao.add(Token.Tipo.And);
        followExpressao.add(Token.Tipo.Igual);
        followExpressao.add(Token.Tipo.Maior);
        followExpressao.add(Token.Tipo.Menor);
        followExpressao.add(Token.Tipo.Virgula);
        followExpressao.add(Token.Tipo.Parenteses_Fecha);
        followExpressao.add(Token.Tipo.Condicionalt);
        followExpressao.add(Token.Tipo.Repeticaod);

        followRelaOpt.add(Token.Tipo.Ponto_Virgula);
        followRelaOpt.add(Token.Tipo.Composto_fim);
        followRelaOpt.add(Token.Tipo.Condicional);
        followRelaOpt.add(Token.Tipo.Operador_Multiplicacao);
        followRelaOpt.add(Token.Tipo.Operador_Divisao);
        followRelaOpt.add(Token.Tipo.And);
        followRelaOpt.add(Token.Tipo.Igual);
        followRelaOpt.add(Token.Tipo.Maior);
        followRelaOpt.add(Token.Tipo.Menor);
        followRelaOpt.add(Token.Tipo.Virgula);
        followRelaOpt.add(Token.Tipo.Parenteses_Fecha);
        followRelaOpt.add(Token.Tipo.Condicionalt);
        followRelaOpt.add(Token.Tipo.Repeticaod);

        followRelacao.add(Token.Tipo.Operador_Soma);
        followRelacao.add(Token.Tipo.Operador_Subtracao);
        followRelacao.add(Token.Tipo.Identificador);
        followRelacao.add(Token.Tipo.Numero_Inteiro);
        followRelacao.add(Token.Tipo.Numero_Real);
        followRelacao.add(Token.Tipo.Parenteses_Abre);
        followRelacao.add(Token.Tipo.Not);

        followExprSimpl.add(Token.Tipo.Ponto_Virgula);
        followExprSimpl.add(Token.Tipo.Composto_fim);
        followExprSimpl.add(Token.Tipo.Condicional);
        followExprSimpl.add(Token.Tipo.Operador_Multiplicacao);
        followExprSimpl.add(Token.Tipo.Operador_Divisao);
        followExprSimpl.add(Token.Tipo.And);
        followExprSimpl.add(Token.Tipo.Igual);
        followExprSimpl.add(Token.Tipo.Maior);
        followExprSimpl.add(Token.Tipo.Menor);
        followExprSimpl.add(Token.Tipo.Virgula);
        followExprSimpl.add(Token.Tipo.Parenteses_Fecha);
        followExprSimpl.add(Token.Tipo.Condicionalt);
        followExprSimpl.add(Token.Tipo.Repeticaod);

        followSinalNOpt.add(Token.Tipo.Identificador);
        followSinalNOpt.add(Token.Tipo.Numero_Inteiro);
        followSinalNOpt.add(Token.Tipo.Numero_Real);
        followSinalNOpt.add(Token.Tipo.Parenteses_Abre);
        followSinalNOpt.add(Token.Tipo.Not);

        followSinalOpt.add(Token.Tipo.Identificador);
        followSinalOpt.add(Token.Tipo.Numero_Inteiro);
        followSinalOpt.add(Token.Tipo.Numero_Real);
        followSinalOpt.add(Token.Tipo.Parenteses_Abre);
        followSinalOpt.add(Token.Tipo.Not);

        followTermoLoop.add(Token.Tipo.Ponto_Virgula);
        followTermoLoop.add(Token.Tipo.Composto_fim);
        followTermoLoop.add(Token.Tipo.Condicional);
        followTermoLoop.add(Token.Tipo.Operador_Multiplicacao);
        followTermoLoop.add(Token.Tipo.Operador_Divisao);
        followTermoLoop.add(Token.Tipo.And);
        followTermoLoop.add(Token.Tipo.Igual);
        followTermoLoop.add(Token.Tipo.Maior);
        followTermoLoop.add(Token.Tipo.Menor);
        followTermoLoop.add(Token.Tipo.Virgula);
        followTermoLoop.add(Token.Tipo.Parenteses_Fecha);
        followTermoLoop.add(Token.Tipo.Condicionalt);
        followTermoLoop.add(Token.Tipo.Repeticaod);

        followTermo.add(Token.Tipo.Ponto_Virgula);
        followTermo.add(Token.Tipo.Composto_fim);
        followTermo.add(Token.Tipo.Condicional);
        followTermo.add(Token.Tipo.Operador_Multiplicacao);
        followTermo.add(Token.Tipo.Operador_Divisao);
        followTermo.add(Token.Tipo.And);
        followTermo.add(Token.Tipo.Igual);
        followTermo.add(Token.Tipo.Maior);
        followTermo.add(Token.Tipo.Menor);
        followTermo.add(Token.Tipo.Virgula);
        followTermo.add(Token.Tipo.Parenteses_Fecha);
        followTermo.add(Token.Tipo.Condicionalt);
        followTermo.add(Token.Tipo.Repeticaod);

        followFatorLoop.add(Token.Tipo.Ponto_Virgula);
        followFatorLoop.add(Token.Tipo.Composto_fim);
        followFatorLoop.add(Token.Tipo.Condicional);
        followFatorLoop.add(Token.Tipo.Operador_Multiplicacao);
        followFatorLoop.add(Token.Tipo.Operador_Divisao);
        followFatorLoop.add(Token.Tipo.And);
        followFatorLoop.add(Token.Tipo.Igual);
        followFatorLoop.add(Token.Tipo.Maior);
        followFatorLoop.add(Token.Tipo.Menor);
        followFatorLoop.add(Token.Tipo.Virgula);
        followFatorLoop.add(Token.Tipo.Parenteses_Fecha);
        followFatorLoop.add(Token.Tipo.Condicionalt);
        followFatorLoop.add(Token.Tipo.Repeticaod);

        followVariavel.add(Token.Tipo.Ponto_Virgula);
        followVariavel.add(Token.Tipo.Composto_fim);
        followVariavel.add(Token.Tipo.Condicional);
        followVariavel.add(Token.Tipo.Operador_Multiplicacao);
        followVariavel.add(Token.Tipo.Operador_Divisao);
        followVariavel.add(Token.Tipo.And);
        followVariavel.add(Token.Tipo.Igual);
        followVariavel.add(Token.Tipo.Maior);
        followVariavel.add(Token.Tipo.Menor);
        followVariavel.add(Token.Tipo.Virgula);
        followVariavel.add(Token.Tipo.Parenteses_Fecha);
        followVariavel.add(Token.Tipo.Condicionalt);
        followVariavel.add(Token.Tipo.Repeticaod);

        followFator.add(Token.Tipo.Ponto_Virgula);
        followFator.add(Token.Tipo.Composto_fim);
        followFator.add(Token.Tipo.Condicional);
        followFator.add(Token.Tipo.Operador_Multiplicacao);
        followFator.add(Token.Tipo.Operador_Divisao);
        followFator.add(Token.Tipo.And);
        followFator.add(Token.Tipo.Igual);
        followFator.add(Token.Tipo.Maior);
        followFator.add(Token.Tipo.Menor);
        followFator.add(Token.Tipo.Virgula);
        followFator.add(Token.Tipo.Parenteses_Fecha);
        followFator.add(Token.Tipo.Condicionalt);
        followFator.add(Token.Tipo.Repeticaod);
        
        followExprOpt.add(Token.Tipo.Ponto_Virgula);
        followExprOpt.add(Token.Tipo.Composto_fim);
        followExprOpt.add(Token.Tipo.Condicional);
        followExprOpt.add(Token.Tipo.Operador_Multiplicacao);
        followExprOpt.add(Token.Tipo.Operador_Divisao);
        followExprOpt.add(Token.Tipo.And);
        followExprOpt.add(Token.Tipo.Igual);
        followExprOpt.add(Token.Tipo.Maior);
        followExprOpt.add(Token.Tipo.Menor);
        followExprOpt.add(Token.Tipo.Virgula);
        followExprOpt.add(Token.Tipo.Parenteses_Fecha);
        followExprOpt.add(Token.Tipo.Condicionalt);
        followExprOpt.add(Token.Tipo.Repeticaod);
        
        followListaExpr.add(Token.Tipo.Parenteses_Fecha);
        
        followExprLoop.add(Token.Tipo.Parenteses_Fecha);
        
        firstBloco.add(Token.Tipo.Identificador);
        firstBloco.add(Token.Tipo.Procedure);
        firstBloco.add(Token.Tipo.Composto_inicio);
        firstIdentLoop.add(Token.Tipo.Virgula);
        firstParamForm.add(Token.Tipo.Parenteses_Abre);
        firstSecParamForm.add(Token.Tipo.Variavel);
        firstSecParamForm.add(Token.Tipo.Identificador);
        firstComando.add(Token.Tipo.Identificador);
        firstComando.add(Token.Tipo.Composto_inicio);
        firstComando.add(Token.Tipo.Condicional);
        firstComando.add(Token.Tipo.Repeticao);
        firstCmdLoop.add(Token.Tipo.Ponto_Virgula);
        firstOptIdent.add(Token.Tipo.Atribuicao);
        firstOptIdent.add(Token.Tipo.Operador_Soma);
        firstOptIdent.add(Token.Tipo.Operador_Subtracao);
        firstOptIdent.add(Token.Tipo.Identificador);
        firstOptIdent.add(Token.Tipo.Numero_Inteiro);
        firstOptIdent.add(Token.Tipo.Parenteses_Abre);
        firstOptIdent.add(Token.Tipo.Not);
        firstExpressao.add(Token.Tipo.Operador_Soma);
        firstExpressao.add(Token.Tipo.Operador_Subtracao);
        firstExpressao.add(Token.Tipo.Identificador);
        firstExpressao.add(Token.Tipo.Numero_Inteiro);
        firstExpressao.add(Token.Tipo.Parenteses_Abre);
        firstExpressao.add(Token.Tipo.Not);
        
    }
    private void program() {
        System.out.println("program");

        if (t.tipo == Token.Tipo.Programa) {
            getToken();
        }else{
            List<Token.Tipo> sinc_array = new ArrayList<Token.Tipo>();
            sinc_array.add(Token.Tipo.Identificador);
            modoPanico(Token.Tipo.Programa, sinc_array );
        }
System.out.println("ident");
        if (t.tipo == Token.Tipo.Identificador) {
            getToken();
        }else{
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.add(Token.Tipo.Ponto_Virgula);
            modoPanico(Token.Tipo.Identificador, sinc_array);
        }
System.out.println(";");

        if (t.tipo == Token.Tipo.Ponto_Virgula) {
            getToken();
        }else{
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstBloco);
            sinc_array.add(Token.Tipo.Composto_fim_codigo);
            modoPanico(Token.Tipo.Ponto_Virgula, sinc_array);
        }

        bloco();

        if (t.tipo == Token.Tipo.Composto_fim_codigo) {
            System.out.println("fim");        
        }else{
            System.out.println("biscuit lixo");        
            List<Token.Tipo> sinc_array = new ArrayList<>();
            modoPanico(Token.Tipo.Composto_fim_codigo, sinc_array);
        }
   
    }

    private void bloco() {
        //  System.out.println("bloco");
        parteDeclVar();
        declProc();
        cmdComposto();
    }

    private void tipo() {
        //System.out.println("tipo");
        if (t.tipo == Token.Tipo.Identificador) {
            getToken();
        } else {
            modoPanico(Token.Tipo.Identificador, followTipo);
        }
    }

    private void parteDeclVar() {
        //System.out.println("partDeclVar");
        // Se o first de declvar for o próximo token a ver é executada
        if (t.tipo == Token.Tipo.Identificador) {
            declVar();
            if (t.tipo == Token.Tipo.Ponto_Virgula) {
                getToken();
            }else{
                modoPanico(Token.Tipo.Ponto_Virgula, followParteDeclVar);
            } 
            parteDeclVar();
        } 
        // Senão nada ocorre pois existe movimento vazio
        else {
            //vazio
        }
    }

    private void declVar() {
        //System.out.println("declVar");        
        tipo();
        listaIdent();
    }

    private void listaIdent() {
        //System.out.println("listaIdent");
        if (t.tipo == Token.Tipo.Identificador) {
            getToken();
        } else {
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstIdentLoop);
            sinc_array.addAll(followListaIdent);           
            modoPanico(Token.Tipo.Identificador, sinc_array);
        }
        identLoop();
    }

    private void identLoop() {
        //System.out.println("identLoop");
        if (t.tipo == Token.Tipo.Virgula) {
            getToken();
            if (t.tipo == Token.Tipo.Identificador) {
                getToken();              
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(firstIdentLoop);
                sinc_array.addAll(followIdentLoop);           
                modoPanico(Token.Tipo.Identificador, sinc_array);
            }
            identLoop();
            
        } else {
            //Vazio
        }
    }

    //procedure identificador PARAM_FORM ; BLOCO ; | ε
    private void declProc() {
        //System.out.println("declProc");
        if (t.tipo == Token.Tipo.Procedure) {
            getToken();
            if (t.tipo == Token.Tipo.Identificador) {
                getToken();
                
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(firstParamForm);
                sinc_array.add(Token.Tipo.Ponto_Virgula);
                sinc_array.addAll(followDeclProc);
                modoPanico(Token.Tipo.Identificador, sinc_array);
            }
            paramForm();
            if (t.tipo == Token.Tipo.Ponto_Virgula) {
                getToken();                        
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(followDeclProc);
                modoPanico(Token.Tipo.Ponto_Virgula, sinc_array);
            }
            bloco();
            if (t.tipo == Token.Tipo.Ponto_Virgula) {
                getToken();                        
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(followDeclProc);
                modoPanico(Token.Tipo.Ponto_Virgula, sinc_array);
            }
                    
        } else {
            //vazio
        }
    }

    //(SEC_PARAM_FORM SEC_PARAM_FORM_LOOP ) | ε
    private void paramForm() {
        //System.out.println("paramForm");
        if (t.tipo == Token.Tipo.Parenteses_Abre) {
            getToken();
            secParamForm();
            secParamFormLoop();
            if (t.tipo == Token.Tipo.Parenteses_Fecha) {
                getToken();
                
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(followParamForm);
                modoPanico(Token.Tipo.Parenteses_Fecha, sinc_array);
            }
        } 
        else {
            //vazio
        }
        
    }

    //; SEC_PARAM_FORM SEC_PARAM_FORM_LOOP | ε
    private void secParamFormLoop() {
        // System.out.println("secParamFormLoop");
        if (t.tipo == Token.Tipo.Ponto_Virgula) {
            getToken();
            secParamForm();
            secParamFormLoop();
        } else {
            //vazio
        }

    }

    //var LISTA_IDENT : identificador | LISTA_IDENT : identificador
    private void secParamForm() {
        //System.out.println("secParamForm");
        if (t.tipo == Token.Tipo.Variavel) {
            getToken();
            listaIdent();
            if (t.tipo == Token.Tipo.Dois_Pontos) {
                getToken();
                 
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.add(Token.Tipo.Identificador);
                sinc_array.addAll(followSecParamForm);
                modoPanico(Token.Tipo.Dois_Pontos, sinc_array);
            }
            if (t.tipo == Token.Tipo.Identificador) {
                    getToken();
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(followSecParamForm);
                modoPanico(Token.Tipo.Identificador, sinc_array);
            }
        } else {
            listaIdent();
            if (t.tipo == Token.Tipo.Dois_Pontos) {
                getToken();
            }
            else{
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.add(Token.Tipo.Identificador);
                sinc_array.addAll(followSecParamForm);
                modoPanico(Token.Tipo.Dois_Pontos, sinc_array);
            }
            if (t.tipo == Token.Tipo.Identificador) {
                getToken();
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(followSecParamForm);
                modoPanico(Token.Tipo.Identificador, sinc_array);
            }
        }

    }

    //begin COMANDO CMD_LOOP end
    private void cmdComposto() {
        //System.out.println("cmdComposto");
        if (t.tipo == Token.Tipo.Composto_inicio) {
            getToken();
        }else{
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstComando);
            modoPanico(Token.Tipo.Composto_inicio, sinc_array);
        }
        comando();
        cmdLoop();
        if (t.tipo == Token.Tipo.Composto_fim) {
            getToken();
        } else {
            System.out.println("panico"); 
            System.out.println("tipo"+ t.tipo.name()); 
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(followCmdComposto);
            modoPanico(Token.Tipo.Composto_fim, sinc_array);
        }
    }

    //; COMANDO CMD_LOOP | ε
    private void cmdLoop() {
        //System.out.println("cmdLoop");
        if (t.tipo == Token.Tipo.Ponto_Virgula) {
            getToken();
            comando();
            cmdLoop();
        } else {
            //vazio
        }

    }

    //START_IDENT | CMD_COMPOSTO | CMD_COND | CMD_REP
    private void comando() {
        //System.out.println("comando");
        //checking first of START_IDENT
        if (t.tipo == Token.Tipo.Identificador) {
            startIdent();
        } 
        //checking first of cmdComposto
        else if (t.tipo == Token.Tipo.Composto_inicio) {
            cmdComposto();
        } 
        //checking first of cmdCond
        else if (t.tipo == Token.Tipo.Condicional) {
            cmdCond();
        }
        //checking first of cmdRep
        else if (t.tipo == Token.Tipo.Repeticao) {
            cmdRep();
        } else {
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(followComando);
            modoPanico(Token.Tipo.Identificador, sinc_array);
        }
    }

    //identificador OPT_IDENT
    private void startIdent() {
        //System.out.println("startIdent");
        if (t.tipo == Token.Tipo.Identificador) {
            getToken();
        }else {
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstOptIdent);
            sinc_array.addAll(followStartIdent);
            modoPanico(Token.Tipo.Identificador, sinc_array);
        }
        optIdent();
    }

    //:= EXPRESSAO | OPT_LISTA_EXPR | EXPR_OPT
    private void optIdent() {
        //System.out.println("optIdent");
        if (t.tipo == Token.Tipo.Atribuicao) {
            getToken();
            expressao();
        } 
        //checking firsts of optListaExp
        else if (t.tipo == Token.Tipo.Parenteses_Abre) {
            optListaExp();
        }
        //checking firsts of exprOpt +, -,  identificador, numero, (, not
        else if (t.tipo == Token.Tipo.Parenteses_Abre ||
                    t.tipo == Token.Tipo.Operador_Soma ||
                    t.tipo == Token.Tipo.Operador_Subtracao ||
                    t.tipo == Token.Tipo.Identificador ||
                    t.tipo == Token.Tipo.Numero_Inteiro ||
                    t.tipo == Token.Tipo.Not){
            exprOpt();
        } else {
            // first de optIdent tem vazio 
        }

    }

    //( LISTA_EXPR ) | ε
    private void optListaExp() {
        //System.out.println("optListaExp");
        if (t.tipo == Token.Tipo.Parenteses_Abre) {
            getToken();
            listaExp();
            if (t.tipo == Token.Tipo.Parenteses_Fecha) {
                getToken();
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(followOptListaExpr);
                modoPanico(Token.Tipo.Parenteses_Fecha, sinc_array);
            }
        } else {
            //vazio
        }
    }

    // CMD_COND -> if EXPRESSAO then COMANDO ELSE_CMD_OPT
    private void cmdCond() {
        //System.out.println("cmdCond");
        if (t.tipo == Token.Tipo.Condicional) {
            getToken();
        }
        else{
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstExpressao);
            modoPanico(Token.Tipo.Condicional, sinc_array);
        }
        expressao();
        if (t.tipo == Token.Tipo.Condicionalt) {
            getToken();
        }
        else{
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstComando);
            modoPanico(Token.Tipo.Condicionalt, sinc_array);
        }
        comando();
        elseCmdOpt();
    }

    // ELSE_CMD_OPT -> else COMANDO | ε
    private void elseCmdOpt() {
        //System.out.println("elseCmdOpt");
        if (t.tipo == Token.Tipo.Condicionale) {
            getToken();
            comando();
        } else {
            //vazio
        }
    }

    // CMD_REP -> while EXPRESSAO do COMANDO
    private void cmdRep() {
        //System.out.println("cmdRep");
        if (t.tipo == Token.Tipo.Repeticao) {
            getToken();
        }
        else{
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstExpressao);
            modoPanico(Token.Tipo.Repeticao, sinc_array);
        }
        expressao();
        if (t.tipo == Token.Tipo.Repeticaod) {
            getToken();
        }else{
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(firstComando);
            modoPanico(Token.Tipo.Repeticao, sinc_array);
        }
        comando();
    }

    // EXPRESSAO -> EXPR_SIMPL RELA_OPT
    private void expressao() {
        //System.out.println("expressao");
        expSimpl();
        relaOpt();
    }

    // RELA_OPT -> RELACAO EXPR_SIMPL | ε
    private void relaOpt() {
        // System.out.println("relaOpt");
        //checking first of relacao
        if (t.tipo == Token.Tipo.Igual ||
                t.tipo == Token.Tipo.Maior ||
                t.tipo == Token.Tipo.Menor
                //FALTA MAIOR IGUAL E MENOR IGUAL
                //t.tipo == Token.Tipo
                ) {
            relacao();
            expSimpl();
        } else {
            // vazio
        }
    }

    // RELACAO -> = | <> | < | <= | >= | >
    private void relacao() {
        //System.out.println("relacao");
        if (t.tipo == Token.Tipo.Maior) {
            getToken();
            
        } else if (t.tipo == Token.Tipo.Menor) {
            getToken();
            
        } else if (t.tipo == Token.Tipo.Igual) {
            getToken();
            
        } else {
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(followRelacao);
            modoPanico(Token.Tipo.Maior, sinc_array);
        }
    }

    // EXPR_SIMPL -> SINAL_OPT TERMO TERMO_LOOP
    private void expSimpl() {
        //System.out.println("exprSimpl");
        sinalOpt();
        termo();
        termoLoop();
    }

    // SINAL_N_OPT ->  + | - 
    private void sinalNOpt() {
        //System.out.println("sinalNOpt");
        if (t.tipo == Token.Tipo.Operador_Soma) {
            getToken();
        } else if (t.tipo == Token.Tipo.Operador_Subtracao) {
            getToken();
        } else {
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(followSinalNOpt);
            modoPanico(Token.Tipo.Operador_Soma, sinc_array);
        }

    }

    // SINAL_OPT ->  SINAL_N_OPT | ε
    private void sinalOpt() {
        //System.out.println("sinalOpt");
        // checking first of sinalNOPt
        if (t.tipo == Token.Tipo.Operador_Subtracao ||
                t.tipo == Token.Tipo.Operador_Soma){
            sinalNOpt();
            
        } else {
            // vazio
        }
    }

    // TERMO_LOOP -> SINAL_N_OPT TERMO TERMO_LOOP | or TERMO TERMO_LOOP | ε
    private void termoLoop() {
        //System.out.println("termoLoop");
        // checking first of sinalNOPt
        if (t.tipo == Token.Tipo.Operador_Subtracao ||
                t.tipo == Token.Tipo.Operador_Soma){
            sinalNOpt();
            termo();
            termoLoop();
        } else if (t.tipo == Token.Tipo.Or) {
            termo();
            termoLoop();
        } else {
            //vario
        }
    }

    // TERMO -> FATOR FATOR_LOOP
    private void termo() {
        //System.out.println("termo");
        fator();
        fatorLoop();
    }

    // FATOR_LOOP -> * FATOR FATOR_LOOP | div FATOR FATOR_LOOP | 
    // and FATOR FATOR_LOOP | ε
    private void fatorLoop() {
        //System.out.println("fatorLoop");
        if (t.tipo == Token.Tipo.Operador_Multiplicacao) {
            getToken();
            fator();
            fatorLoop();
        } else if (t.tipo == Token.Tipo.Div) {
            getToken();
            fator();
            fatorLoop();
        } else if (t.tipo == Token.Tipo.And) {
            getToken();
            fator();
            fatorLoop();
        } else {
            //vazio
        }

    }

    // VARIAVEL -> identificador EXPR_OPT
    private void variavel() {
        //System.out.println("variavel");
        if (t.tipo == Token.Tipo.Identificador) {
            getToken();
            exprOpt();
        } else {
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(followVariavel);
            modoPanico(Token.Tipo.Identificador, sinc_array);
        }
    }

    // FATOR -> VARIAVEL | numero | ( EXPRESSAO ) | not FATOR
    private void fator() {
        //System.out.println("fator");
        // checking variavel first
        if (t.tipo == Token.Tipo.Identificador) {
            variavel();
        } else if (t.tipo == Token.Tipo.Numero_Real
                || t.tipo == Token.Tipo.Numero_Inteiro) {
            getToken();
        } else if (t.tipo == Token.Tipo.Parenteses_Abre) {
            getToken();
            expressao();
            if (t.tipo == Token.Tipo.Parenteses_Fecha) {
                getToken();
            } else {
                List<Token.Tipo> sinc_array = new ArrayList<>();
                sinc_array.addAll(followFator);
                modoPanico(Token.Tipo.Parenteses_Fecha, sinc_array);
            }
        } else if (t.tipo == Token.Tipo.Not) {
            getToken();
            fator();
        } else {
            List<Token.Tipo> sinc_array = new ArrayList<>();
            sinc_array.addAll(followFator);
            modoPanico(Token.Tipo.Identificador, sinc_array);
        }
    }

    // EXPR_OPT -> EXPRESSAO | ε
    private void exprOpt() {
        // System.out.println("exprOpt");
        //checking first of expressao
        if (t.tipo == Token.Tipo.Parenteses_Abre ||
                t.tipo == Token.Tipo.Operador_Soma ||
                t.tipo == Token.Tipo.Operador_Subtracao ||
                t.tipo == Token.Tipo.Identificador ||
                t.tipo == Token.Tipo.Numero_Inteiro ||
                t.tipo == Token.Tipo.Not){
            expressao();
        } else {
            //vazio
        }
    }

    // LISTA_EXPR -> EXPRESSAO EXPR_LOOP
    private void listaExp() {
        // System.out.println("listaExp");
        expressao();
        exprLoop();
    }

    // EXPR_LOOP -> , EXPRESSAO EXPR_LOOP | ε
    private void exprLoop() {
        // System.out.println("exprLoop");
        if (t.tipo == Token.Tipo.Virgula) {
            getToken();
            expressao();
            exprLoop();
        } else {
            // Vazio
        }

    }

    private void getToken() {
        t = al.getToken();
        /* if (t != null) {
            System.out.println("LEXEMA: " + t.lexema);
            System.out.println("LEXEMA: " + t.tipo);
        }*/
    }

    private boolean modoPanico(Token.Tipo esperado, List<Token.Tipo> follow) {
        if (t != null) {
            logErro(t, esperado.name());
        } else {
            return false;
        }
        //System.out.println("Entrando em modo de panico");
        //System.out.println("Era esperado um token do tipo" + tipo);
        follow.add(0,t.tipo);
        while (true) {
            
            for (Token.Tipo f : follow) {
                if (t.tipo == f) {
                    return true;
                }
            }
            Token token_aux = al.getToken();
            if (token_aux != null){
                t = token_aux;
            }else {
                return false;
            }
        }

        // System.out.println("Saindo do modo de panico");
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
