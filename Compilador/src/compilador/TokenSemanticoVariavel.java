package compilador;

public class TokenSemanticoVariavel extends Token {

	public static enum TipoVar {
		Inteiro("integer|int"), Real("real"), Booleano("boolean");

		public final String regex;

		// E considerada uma boa pratica de programacao proteger o construtor do
		// enum
		// Garantindo assim tipos estaticos
		private TipoVar(String regex){
                    this.regex = regex;
                }
                // função para verificar o tipo da variável
                public static TipoVar tipoVarByLexema(String lexema){
                    for (TipoVar tipo: TipoVar.values() ){
                        if (lexema.matches(tipo.regex)){
                            return tipo;
                        }
                    }
                    return null;
                }
	}

	public TipoVar tipo_var = null;
	//Para Cada token semantico, será atribuido um id gerado durante a analise
	//Dai a verificação de escopo passa a ser uma simples verificação de ID
	public int escopo = 0;
	//Trataremos qualquer valor como String, maneira mais facil 
	public String valor;
	public boolean usada = false;
        // endereco de memoria relativo
        public int end_rel = -1;
	
	public TokenSemanticoVariavel(Token t) {
		super(t.lexema, t.linha, t.coluna_ini, t.coluna_fim);
		// TODO Auto-generated constructor stub
	}
	public TokenSemanticoVariavel(
                Token t,
                TipoVar tipo_var,
                String valor,
                int escopo,
                int end_rel
        ) {
		super(t.lexema, t.linha, t.coluna_ini, t.coluna_fim);
		this.tipo_var = tipo_var;
                this.valor = valor;
                this.escopo = escopo;
                this.end_rel = end_rel;
	}
	public void setEscopo(int escop){
		this.escopo = escop;
	}
	
	//Define o tipo da variavel, as escolhas são booleano, inteiro e real
	//Poderia ser feito através de sobrecarga de operador, mas isso iria requerer
	//uma classe de tipo e seria necessário passar como parametro o TipoVar desejado
	public void setBooleano(){
		this.tipo_var = TipoVar.Booleano;
	}
	
	public void setInteiro(){
		this.tipo_var = TipoVar.Inteiro;
	}
	
	public void setReal(){
		this.tipo_var = TipoVar.Real;
	}
	
}
