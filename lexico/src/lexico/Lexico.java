/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 *
 * @author gabrielozaki
 */
public class Lexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Tabela de simbolos, no caso da calculadora, qualquer coisa que nao seja digito ou numero
        String[] tabela_simbolos = {"+","-","*","/","(",")"};
        
        //String de teste, no futuro substituir por um input do user
        String str = "2.2*2+(7/2)-2";
        
        //Tabela de tokens, aqui vamos armazenar os tokens classificados
        List<String> tokens = new ArrayList<String>();
        
        //Tabela de tokens nao classificados, por exemplo:
        //Salvamos nesta tabela o valor 3.2 e na tabela de tokens armazenamos Numero_Real
        //Tokens_nao_class | Tokens
        //3.2 | Numero_Real
        // + | Operador_Soma
        //( |  Parenteses_Abre
        List<String> tokens_nao_classifcados = new ArrayList<String>();
        
        //Aqui vamos dividir os elementos em tokens nao classificados
        //String temporaria para auxiliar no laço, ela vai basicamente servir para armazenar numeros, maiores informações no laço
        String tmp="";
        //
        
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
                  tokens_nao_classifcados.add(tmp);
              //Limpa o tmp
              tmp = "";
              //Adicionamos o simbolo, problema é que é necessário converter char para string porque o java nao faz automaticamente
              tokens_nao_classifcados.add(String.valueOf(ch));
          }
        }
        //Repetimos esse trecho pois o ultimo valor pode ser um numero nao inserido na tabela
        if(tmp != "")
                  tokens_nao_classifcados.add(tmp);
        
        //percorre todos os tokens nao classificados
        for( String token: tokens_nao_classifcados ){
            //imprime os meninoes
            //System.out.println(token);
            if(token.matches("[0-9]+.[0-9]+")){
                tokens.add("Numero_Real");
                continue;
            }
            
            if(token.matches("[0-9]+")){
                tokens.add("Numero_Inteiro");
                continue;
            }
            
            if(token.matches("[+]")){
                tokens.add("Operador_Soma");
                continue;
            }
            
            if(token.matches("[-]")){
                tokens.add("Operador_Subtracao");
                continue;
            }
            
            if(token.matches("[*]")){
                tokens.add("Operador_Multiplicacao");
                continue;
            }
            
            if(token.matches("[/]")){
                tokens.add("Operador_Divisao");
                continue;
            }
            
            if(token.matches("[(]")){
                tokens.add("Parenteses_Abre");
                continue;
            }
            
            if(token.matches("[}]")){
                tokens.add("Parenteses_Fecha");
                continue;
            }
        }
        
        for(int i=0;i<tokens.size();i++ ){
            System.out.println(tokens_nao_classifcados.get(i)+"|"+tokens.get(i));
        }
/*
        
        
        // The Regular expression (Finds {word} tokens)
        Pattern pt = Pattern.compile("(-?)\\d+|\\+|\\*|/|-|\\(|\\)");

        // Match the string with the pattern
        Matcher m = pt.matcher(str);

        // If results are found
        while (m.find()) {
            System.out.println(m);
            System.out.println(m.group()); // {World}
        }*/
    }
    
}
