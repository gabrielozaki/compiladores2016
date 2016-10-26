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
        //Seguindo a aula: http://www2.fct.unesp.br/docentes/dmec/olivete/compiladores/arquivos/Aula1.pdf
        //Alfabeto = {0,1,2,3,4,5,6,7,8,9,.,(,),+,-,*,/,\b} 
        //Definicao dos tipos de tokens
        String[] tipos_tokens = {"Numero Real","Numero Inteiro","Operador_Soma","Operador_Subtracao","Operador_Multiplicacao","Operador_Divisao","Parenteses_Abre","Parenteses_Fecha"};
        //Definicao para identificar cada tipo de token, a relacao e de 1:1 
        //Para Reais, esse regex exige que tenha pelo menos um numero antes do . e pelo menos um numero depois, logo .3 ou 2. nao sao validos
        //Para Inteiros, esse regex exige que tenha pelo menos um numero    
        String[] tipos_tokens_regex = {"[0-9]+.[0-9]+","[0-9]+","[+]","[-]","[*]","[/]","[(]","[)]"};
        
        //String de teste, no futuro substituir por um input do user
        String str = "2.2*2+ (7/2)-2";
        //String str = "3.2 + (2 * 12.01)";
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
              //ignoramos os espacos em branco
              if(ch == ' ')
                  continue;
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
            
            //Efetua o preenchmento da lista tokens
            for(int i=0;i<tipos_tokens.length;i++)
                //Se o token possuir a expressao regular, adiciona a lista de tokens
                if(token.matches(tipos_tokens_regex[i]))
                {
                    //adiciona a classificacao a lista de tokens
                    tokens.add(tipos_tokens[i]);
                    //Evita que um numero real seja classificado como inteiro e tambem evita processamento desnecessario
                    //pois um token so e classificado uma vez
                    break;
                }
        }
        
        //Imprime ambas as listas mostrando as classificacoes
        for(int i=0;i<tokens.size();i++ ){
            System.out.println(tokens_nao_classifcados.get(i)+"|"+tokens.get(i));
        }
    }
    
}
