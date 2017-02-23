package compilador;

import java.util.ArrayList;

public class AnalisadorSemantico {

	// Esse é um contador de Id e não um id
	// Para cada procedimento declarado será incrementado um id
	// Isso vai servir para verificarmos o escopo das variaveis
	int id = 0;

	// Lista de Tokens de variaveis e de procedimentos, basicamente seria as
	// nossas tabelas
	// São separadas para acelerar e facilitar as buscas
	ArrayList<TokenSemanticoProcedimento> procedimentos = new ArrayList<>();
	ArrayList<TokenSemanticoVariavel> variaveis = new ArrayList<>();

	public AnalisadorSemantico() {
		// Construtor vazio
	}

	// Adiciona novos procedimentos
	public boolean adicionaProcedimento(Token t) {
		// Verifica caso o procedimento já existe, pois a linguagem não permite
		// declarar um procedimento duas vezes
		// Logo isso é um erro semantico
		if (verificaExistenciaProcedimento(t)) {
			return false;
		}

		procedimentos.add(new TokenSemanticoProcedimento(t));
		id++;
		return true;
	}

	public boolean adicionaVariavel(Token t) {
		int aux;
		// Verifica caso a variavel já existe naquele escopo
		if (verificaExistenciaVar(t, id)) {
			return false;
		}

		variaveis.add(new TokenSemanticoVariavel(t));
		// Pega a ultima variavel adicionada
		aux = variaveis.size() - 1;
		variaveis.get(aux).escopo = id;
		return true;
	}

	public boolean verificaExistenciaVar(Token t, int escopo) {
		for (TokenSemanticoVariavel v : variaveis) {
			// Verifica se a variavel já existe naquele escopo
			if (v.lexema == t.lexema && v.escopo == escopo) {
				return true;
			}
		}
		return false;
	}

	// Retorna o indice da variavel buscada
	public int getIndexVariavel(Token t, int escopo) {
		int i = 0;
		for (TokenSemanticoVariavel v : variaveis) {
			// compara até achar a variavel buscada
			if (v.lexema == t.lexema && v.escopo == escopo) {
				// retorna o indice dela no arraylist
				return i;
			}
			i++;
		}
		return 0;
	}

	
	//Importante para verificar se um procedimento foi criado antes
	public boolean verificaExistenciaProcedimento(Token t) {
		for (TokenSemanticoProcedimento p : procedimentos) {
			// Verifica se o procedimento já existe naquele escopo
			if (p.lexema == t.lexema) {
				return true;
			}
		}
		return false;
	}

}
