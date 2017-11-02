package Sistema;

public abstract class Pessoa {
	
	public abstract String getMatricula();
	public abstract void setMatricula(String matricula);
	public abstract DadosPessoais getDadosPessoais();
	public abstract void setDadosPessoais(DadosPessoais dados);
	public abstract int getId();
	public abstract void setId(int id);
}
