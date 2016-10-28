/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabrielozaki
 */
public class AnalisadorLexico {

    public static void AnalisadorLexico() {
        //So cria
    }

    public List<Token> analisa(String str) {
        //Cria uma lista de tokens, eles são inicializados com os lexiomas mais informações referentes(ver construtor)
        List<Token> tokens = new ArrayList<>();

        //Aqui vamos dividir os elementos em tokens nao classificados
        //String temporaria para auxiliar no laço, ela vai basicamente servir para armazenar numeros, maiores informações no laço
        String tmp = "";
        //auxiliares para armazenar as linhas e colunas
        int i = 1;
        int ini = 1;
        int linha = 1;
        //Boleano para verificar se o lexima contem apenas numeros
        boolean numero;

        //Percorre toda string original caractere a caractere
        for (char ch : str.toCharArray()) {
            //Espacos podem ser ignorados na analise lexica
            if (ch != ' ' && ch != '\t') {
                //Aqui salvamos sequenciamente todos os caracteres ate encontrar algum delimitador
                //Nesse caso ele aceitaria um a.braco.fawofa.flka mas isso sera dado erro na classificacao lexica 
                if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '.') {
                    tmp += ch;
                } else {
                    //Verificamos se existe um conteudo em tmp, se existir adiciona aos tokens nao classificados
                    //Sinal que a interaçao anterior pegou um numero
                    if (tmp != "") {
                        tokens.add(new Token(tmp, linha, ini, i - 1));
                    }

                    //Limpa o tmp
                    tmp = "";
                    ini = i;
                    //ignoramos os espacos em branco
                    if (ch != ' ') {
                        //Pegamos a quebra de linha, sendo que esta define o final de uma equacao
                        if (ch == '\n') {
                            linha++;
                            //Volta ini para 1 pois e uma nova linha
                            ini = 1;
                            //o incremento ocorre no final do laco, entao setamos para 0 para ele ser incrementado para 1
                            i = 0;
                        } else {
                            //Adicionamos o simbolo, problema é que é necessário converter char para string porque o java nao faz automaticamente
                            tokens.add(new Token(String.valueOf(ch), linha, ini, i));
                            //Ini recebe i+1 pois estamos preparando o ini do proximo numero
                            ini = i + 1;
                        }
                    }
                }

            } else if (tmp != "") {
                //Consideramos o espaco como um delimitador entre tokens, porem ele e ignorado
                //por isso aqui ocorre um caso especial, em que o delimitador nao e adicionado aos tokens
                tokens.add(new Token(tmp, linha, ini, i - 1));
                tmp = "";
            }
            //Prepara o i para proxima interacao
            i++;
            /* 
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
              if(ch != ' '){
                 //Pegamos a quebra de linha, sendo que esta define o final de uma equacao
                if(ch == '\n'){
                  linha++;
                  //Volta ini para 1 pois e uma nova linha
                  ini=1;
                  //o incremento ocorre no final do laco, entao setamos para 0 para ele ser incrementado para 1
                  i=0;
                }
                else{
                    //Adicionamos o simbolo, problema é que é necessário converter char para string porque o java nao faz automaticamente
                    tokens.add(new Token(String.valueOf(ch),linha,ini,i));
                    //Ini recebe i+1 pois estamos preparando o ini do proximo numero
                    ini = i+1;
                }
              }
          }
          i++;
        }
        //Repetimos esse trecho pois o ultimo valor pode ser um numero nao inserido na tabela
        if(tmp != "")
            tokens.add(new Token(tmp,linha,ini,i-1));
             */
        }
        return tokens;
    }

}
