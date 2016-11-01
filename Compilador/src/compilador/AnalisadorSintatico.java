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
    
    public static void AnalisadorSintatico(){
        //so cria o construtor
    }
    
    public void populaLexico(String codigoFonte){
        al.tokeniza(codigoFonte);
    }
    
    public List<Token> apenasLexico(){
        return al.getListaToken();
    }
    
            
}
