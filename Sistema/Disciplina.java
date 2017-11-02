package Sistema;

import java.util.*;

public class Disciplina {

    String nome;
    String turno, turma;
    Professor professor;
    List<Aluno> alunos;

    public Disciplina() {
        this("", "", "", new ArrayList<Aluno>());
    }

    public Disciplina(String nome, String turno, String turma, List<Aluno> alunos) {
        this.nome = nome;
        this.turno = turno;
        this.turma = turma;
        this.alunos = alunos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public String toString() {
        return "Nome = " + this.nome + ", Turno=" + this.turno + ", Turma=" + this.turma;
    }
    
    
}
