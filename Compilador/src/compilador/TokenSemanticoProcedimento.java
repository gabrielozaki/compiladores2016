package compilador;

import java.util.ArrayList;

public class TokenSemanticoProcedimento extends Token {

	//Para Cada token semantico, será atribuido um id gerado durante a analise
	//Dai a verificação de escopo passa a ser uma simples verificação de ID
	public int id = 0;
	//Trataremos qualquer valor como String, maneira mais facil 
	public String valor;
	public boolean usada = false;
	public ArrayList<TokenSemanticoVariavel> parametros = new ArrayList<>();
	
	
	public TokenSemanticoProcedimento(Token t) {
		super(t.lexema, t.linha, t.coluna_ini, t.coluna_fim);
		
		// TODO Auto-generated constructor stub
	}
	
	//Define o id do procedimento
	public void setId(int id){
		this.id = id;
	}

	//Cria uma lista com oos parametros do procedimento
	//isso irá ajudar a verificar tipos de parametros
	public void setParametros(ArrayList<TokenSemanticoVariavel> param ){
		for(TokenSemanticoVariavel p : param){
			parametros.add(p);
		}
	}
}
