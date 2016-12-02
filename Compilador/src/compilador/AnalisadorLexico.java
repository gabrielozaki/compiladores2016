/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import compilador.Token.Tipo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author gabrielozaki
 */
public class AnalisadorLexico {

    //Cria uma lista de tokens, eles são inicializados com os lexiomas mais informações referentes(ver construtor)
    //e publico porque podemos querer fazer acesso direto apos a analise
    public List<Token> tokens = new ArrayList<>();
    //O Analisador sintatico vai pedir os tokens um a um
    //Entao o indice serve pra sabermos qual o ultimo token que o sintatico pediu
    private int indice = 0;

    public static void AnalisadorLexico() {
        //So cria
    }

    //Recebe um codigo fonte do Analisador Sintatico e gera uma lista de tokens
    public void tokeniza(String str) {

        //Aqui vamos dividir os elementos em tokens nao classificados
        //String temporaria para auxiliar no laço, ela vai basicamente servir para armazenar numeros, maiores informações no laço
        String tmp = "";
        //auxiliares para armazenar as linhas e colunas
        int i = 1;
        int ini = 1;
        int linha = 1;
        char ultimo = ' ';
        //Boleano para verificar se o lexima contem apenas numeros
        boolean comentario = false;
        
        //Percorre toda string original caractere a caractere
        for (char ch : str.toCharArray()) {
            //Caso estejamos dentro de um comentario de uma linha
            //ele só acaba quando entrar no \n
            //Então pulamos tudo ate achar um \b

            if (ch != '\n' && comentario) {
                continue;
            }
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
                        this.tokens.add(new Token(tmp, linha, ini, i - 1));
                    }

                    //Limpa o tmp
                    tmp = "";
                    ini = i;

                    //ignoramos os espacos em branco
                    //Pegamos a quebra de linha, sendo que esta define o final de uma equacao
                    if (ch == '\n') {
                        linha++;
                        //Volta ini para 1 pois e uma nova linha
                        ini = 1;
                        //o incremento ocorre no final do laco, entao setamos para 0 para ele ser incrementado para 1
                        i = 0;
                        comentario = false;
                    } else if (ch == '=' && ultimo == ':') {
                        //:= é caso especial, seria o token de atribuicao
                        //Por isso removemos o token : cadastrado previamente
                        this.tokens.remove(this.tokens.size() - 1);
                        //E cadastramos o novo
                        //ini -1 é para pegar o inicio do anterior
                        this.tokens.add(new Token(":=", linha, ini - 1, i));
                        //Ini recebe i+1 pois estamos preparando o ini do proximo elemento
                        ini = i + 1;
                    } else if (ch == '/' && ultimo == '/') {
                        //:= é caso especial, seria o token de atribuicao
                        //Por isso removemos o token : cadastrado previamente
                        this.tokens.remove(this.tokens.size() - 1);
                        //E cadastramos o novo
                        //ini -1 é para pegar o inicio do anterior
                        this.tokens.add(new Token("//", linha, ini - 1, i));
                        //Ini recebe i+1 pois estamos preparando o ini do proximo elemento
                        ini = i + 1;
                        comentario = true;
                    } else {

                        //Adicionamos o simbolo, problema é que é necessário converter char para string porque o java nao faz automaticamente
                        this.tokens.add(new Token(String.valueOf(ch), linha, ini, i));
                        //Ini recebe i+1 pois estamos preparando o ini do proximo elemento
                        ini = i + 1;
                    }

                }

            } else if (tmp != "") {
                //Consideramos o espaco como um delimitador entre tokens, porem ele e ignorado
                //por isso aqui ocorre um caso especial, em que o delimitador nao e adicionado aos tokens
                this.tokens.add(new Token(tmp, linha, ini, i - 1));
                tmp = "";
            }
            //Prepara o i para proxima interacao            
            i++;

            ultimo = ch;

        }
        this.classificaTokens();
        this.removeComentarios();
        this.removeStrings();
        this.verificaErros();
        //return this.tokens;
    }

    //Classicamos o token, por exemplo:
    //3.2 | Numero_Real
    // + | Operador_Soma
    //( |  Parenteses_Abre
    public void classificaTokens() {
        //Verificador de erro, caso ele chegue true ao final significa que nenhum regex o classificou
        boolean erro;
        //Percorre todos os tipos de tokens
        for (Token token : this.tokens) {
            erro = true;
            for (Tipo t : Token.Tipo.values()) {
                //Se coincidir com algum, o classifica como sendo daquele tipo
                if (token.lexema.matches(t.regex)) {
                    token.tipo = t;
                    //Evita o erro
                    erro = false;
                    //Como ja encontrou o tipo, nao e necessario processar os outros tipos
                    break;
                }

            }
            //Se chegou aqui, e porque o lexima nao pertence ao alfabeto
            if (erro) {
                token.erro = Token.Erro.Nao_Pertence_Alfabeto;
            }
        }

    }

    //Metodo chamado pelo analisador sintatico
    public Token getToken() {
        //Se o indice for menor que o tamanho da lista
        if (this.indice < this.tokens.size()) {
            return this.tokens.get(this.indice++);
        }
        //retorna null para caso o indice seja maior
        return null;
    }

    public void setIndice(int i) {
        this.indice = i;
    }

    //Retorna apenas a lista de tokens, isso e valido para quando o usuario so quer fazer a analise lexica
    public List<Token> getListaToken() {
        return this.tokens;
    }

    //Elimina comentarios dos tokens
    //Remove tudo depois do { até encontrar um }
    private void removeComentarios() {
        boolean comentario = false;
        Iterator<Token> iter = tokens.listIterator();
        while (iter.hasNext()) {
            Token t = iter.next();
            //Caso estejamos dentro de um comentario e nao achamos o }
            //removemos o token
            if ((!t.lexema.matches("\\}")) && comentario == true) {
                iter.remove();
            } else {
                comentario = false;
            }
            //Inicio do comentario
            if (t.lexema.matches("\\{")) {
                comentario = true;
            }
        }
    }
    
    //Elimina comentarios dos tokens
    //Remove tudo depois do { até encontrar um }
    private void removeStrings() {
        boolean texto = false;
        Iterator<Token> iter = tokens.listIterator();
        while (iter.hasNext()) {
            Token t = iter.next();
            //Caso estejamos dentro de um comentario e nao achamos o }
            //removemos o token
            if ((!t.lexema.matches("\"")) && texto == true) {
                iter.remove();
            } else {
                texto = false;
            }
            //Inicio do comentario
            if (t.lexema.matches("\"")) {
                texto = true;
            }
        }
    }

    //Verifica comentario, como o limpa token elimina tudo depois de abrirmos comentario
    //Checamos se o ultimo token é um {, se for, existe um comentario que não foi fechado
    private void verificaComentario() {
        //Indice do ultimo elemento
        int i = this.tokens.size() - 1;
        if (this.tokens.get(i).lexema.matches("\\{")) {
            this.tokens.get(i).erro = Token.Erro.Comentario_Nao_Fechado;
        }
    }

    //Verifica texto, como o limpa token elimina tudo depois de abrirmos um texto
    //Checamos se o ultimo token é um ", se for, existe um texto que não foi fechado
    private void verificaTexto() {
        //Indice do ultimo elemento
        int i = this.tokens.size() - 1;
        if (this.tokens.get(i).lexema.matches("\"")) {
            this.tokens.get(i).erro = Token.Erro.Texto_Nao_Fechado;
        }
    }
    
    //Limitamos alguns comprimentos de tokens
    //identificadores: 10 caracteres
    //Reais e inteiros: 12 digitos(ponto conta como um)
    private void verificaComprimento() {
        for (Token t : this.tokens) {
            if ((t.tipo == Token.Tipo.Identificador) && t.lexema.length() > 10) {
                t.erro = Token.Erro.Identificador_Overflow;
            }
            if ((t.tipo == Token.Tipo.Numero_Inteiro || t.tipo == Token.Tipo.Numero_Real) && t.lexema.length() > 12) {
                t.erro = Token.Erro.Numero_Overflow;
            }
        }
    }

    //Chama os métodos responsaveis por encontrar os erros
    private void verificaErros() {
        this.verificaComentario();
        this.verificaTexto();
        this.verificaComprimento();
    }
}
