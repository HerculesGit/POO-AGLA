package Sistema;

public class Professor extends Pessoa{

    private int id;
    String matricula, lattes;
    DadosPessoais dadosPessoais;


    public Professor() {
        this("", "", new DadosPessoais());
    }

    public Professor(String matricula, String lattes, DadosPessoais dadosPessoais) {
        this.matricula = matricula;
        this.lattes = lattes;
        this.dadosPessoais = dadosPessoais;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getLattes() {
        return lattes;
    }

    public void setLattes(String lattes) {
        this.lattes = lattes;
    }

    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Id = " + this.id + ", Matricula = " + this.matricula + ", Lattes = " + this.lattes
                + ", DadosPessoais = " + this.dadosPessoais;
    }
}
