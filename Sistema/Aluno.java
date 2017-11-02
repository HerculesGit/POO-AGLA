package Sistema;

import javax.swing.ImageIcon;
import javax.swing.JTable;

public class Aluno extends Pessoa{

	private int id;
	private String matricula, turma, boletim;
	private DadosPessoais dadosPessoais;
	private JTable historico;
	private ImageIcon declaracao;
	
	public Aluno(){
		this("", "", "", new DadosPessoais(), new JTable(), new ImageIcon());
	}
	
	public Aluno(String matricula, String turma, String boletim, DadosPessoais dadosPessoais, JTable historico, ImageIcon declaracao) {
		this.matricula = matricula;
		this.turma = turma;
		this.boletim = boletim;
		this.dadosPessoais = dadosPessoais;
		this.historico = historico;
		this.declaracao = declaracao;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getAno() {
		return turma;
	}
	
	public void setAno(String ano) {
		this.turma = ano;
	}
	
	public String getBoletim() {
		return boletim;
	}
	
	public void setBoletim(String boletim) {
		this.boletim = boletim;
	}
	
	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}
	
	public void setDadosPessoais(DadosPessoais dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}
	
	public JTable getHistorico() {
		return historico;
	}
	
	public void setHistorico(JTable historico) {
		this.historico = historico;
	}
	
	public ImageIcon getDeclaracao() {
		return declaracao;
	}
	
	public void setDeclaracao(ImageIcon declaracao) {
		this.declaracao = declaracao;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

    public String toString() {
        return "Id = " + this.getId()+ ", Matricula = " + this.getMatricula()+ ", Ano = " + this.turma
                + ", Boletim = " + this.boletim + ", Dados Pessoais = " + this.dadosPessoais
                + ", Historico = " + this.historico + ", Declaracao = " + this.declaracao;            
    }
	
}
