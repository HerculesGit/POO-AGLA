package dao;

import static org.junit.Assert.*;

import org.junit.Test;

//import exception.AlunoInexistenteException;
import exception.DisciplinaInexistenteException;
//import exception.DisciplinaIninexistenteException;
import exception.ErroConexaoException;
import exception.TurmaVaziaException;
import object.Aluno;
import object.DadosPessoais;
import object.Disciplina;
import object.Telefone;

public class DisciplinasDAOTest {

	@Test
	public void testAdiciona() {
		fail("Not yet implemented");
	}

	@Test
	public void adicionaAluno() {
		DisciplinasDAO dao = new DisciplinasDAO();
		Aluno a= new Aluno();
		DadosPessoais d=new DadosPessoais();
		Telefone t = new Telefone();
		d.setEmail("h@dce.ufpb.br");
		t.setOperadora("claro");
		t.setPrefixo("83");
		t.setNumero("997718171");
		d.setNome("Herculan");
		d.setTelefone(t);
		a.setDadosPessoais(d);
		
		Disciplina dis = new Disciplina();
		dis.setNome("matemática");
		dis.setTurma("3 ano");
		dis.setTurno("manhã");
		
//		try {
//			dao.adicionaAluno(a, dis);
//		} catch (ErroConexaoException e) {
//			fail(e.getMessage());
//			e.printStackTrace();
//		}
	}

	@Test
	public void pesquisaProfessoresDaDisciplina() {
		DisciplinasDAO dao = new DisciplinasDAO();
		try {
			String [][] s = dao.pesquisaProfessoresDaDisciplina("go");
			for(int i=0;i<s.length;i++) {
				System.out.println(s[i][0] +" - "+s[i][1]+" - "+s[i][2]);
			}
		} catch (ErroConexaoException | DisciplinaInexistenteException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void pesquisaAlunosDaDisciplina() {
		DisciplinasDAO dao = new DisciplinasDAO();
		try {
			String [][] s = dao.pesquisaAlunosDaDisciplina("matemática");
			
			for(int i=0;i<s.length;i++) {
				System.out.println(s[i][0] +" - "+s[i][1]+" - "+s[i][2]);
			}
			
		} catch (ErroConexaoException | DisciplinaInexistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNomeTelefoneEmail() {
		DisciplinasDAO dao = new DisciplinasDAO();
		
		
		
		AlunoDAO adao = new AlunoDAO();
		try {
			adao.test();
		} catch (ErroConexaoException e1) {
			e1.printStackTrace();
			fail(e1.getMessage());
		}
		
		
		
		
//		
//		try {
//			dao.AlunosDaDisciplina("MATEMÁTICA", "5º");
//		} catch (ErroConexaoException | TurmaVaziaException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//			
//		}
	}
	
	
}
