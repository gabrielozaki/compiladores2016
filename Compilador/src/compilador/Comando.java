/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author macbook
 */
public class Comando {
    public static enum TipoComando {
            NADA,
            AMEM,
            ARMZ,
            CRCT,
            CRVL,
            DSVF,
            DSVS,
            CMME,
            CMMA,
            CMIG,
            CONJ,
            DISJ,
            NEGA,
            DIVI,
            SOMA,
            SUBT,
            MULT;

	}
    public String rotulo = "";
    public TipoComando tipo_comando = TipoComando.NADA;
    public String param = "";
    public Comando(String rotulo, TipoComando tipo_comando, String param){
        this.rotulo = rotulo;
        this.tipo_comando = tipo_comando;
        this.param = param;
    }
    public void imprime(){
        System.out.println(rotulo +" "+tipo_comando+ " "+ param);
    }
}
