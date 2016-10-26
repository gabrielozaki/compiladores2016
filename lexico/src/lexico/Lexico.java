/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabrielozaki
 * @author guilherme
 */
public class Lexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String de teste, no futuro substituir por um input do user
        String str = "2.2*2+g(7/2)-2";
        //String str = "3.2 + (2 * 12.01)";
        
        List<Token> tokens = new ArrayList<Token>();
        
        //Aqui vamos dividir os elementos em tokens nao classificados
        //String temporaria para auxiliar no laço, ela vai basicamente servir para armazenar numeros, maiores informações no laço
        String tmp="";
        //auxiliares para armazenar as linhas e colunas
        int i=1;
        int ini=1;
        int linha=1;
        
        //Percorre toda string original caractere a caractere
        for(char ch: str.toCharArray())
        {
          //Comparaçao simples de um caractere nao justifica uso de regex
          if( (ch >= '0' && ch <= '9') || ch == '.' )
          {
              //Aqui isolaremos os numeros, o ponto é adicionado por causa dos reais
              tmp += ch;
              
          }else{
              //Verificamos se existe um conteudo em tmp, se existir adiciona aos tokens nao classificados
              //Sinal que a interaçao anterior pegou um numero
              if(tmp != "")
                  tokens.add(new Token(tmp,linha,ini,i-1));
     
              //Limpa o tmp
              tmp = "";
              ini = i;
              //ignoramos os espacos em branco
              if(ch == ' ' || ch == '\n'){
                  i++;
                  continue;
              }
              //Adicionamos o simbolo, problema é que é necessário converter char para string porque o java nao faz automaticamente
              tokens.add(new Token(String.valueOf(ch),linha,ini,i));
          }
          i++;
        }
        //Repetimos esse trecho pois o ultimo valor pode ser um numero nao inserido na tabela
        if(tmp != "")
            tokens.add(new Token(tmp,linha,ini,i-1));
        
        //Imprime resultados
        System.out.println(str);
        System.out.println("Lexema \t| Token \t| Linha \t| Col inicial \t| Col Final");
        for(Token token:tokens){
            //System.out.println(token.lexema);
            token.classificaToken();
            if(token.erro == null)
                System.out.println(token.lexema+" \t| "+token.tipo.name()+" \t| "+token.linha+" \t| "+token.coluna_ini+" \t| "+token.coluna_fim);
            else
                System.out.println(token.lexema+" \t| ---------- \t| "+token.linha+" \t| "+token.coluna_ini+" \t| "+token.coluna_fim+" \t| "+token.erro.name());
        }
        
    }
}
