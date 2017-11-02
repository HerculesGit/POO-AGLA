package dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import exception.AlunoInexistenteException;
import exception.AlunoJaExisteException;
import exception.ErroConexaoException;
import exception.ListaVaziaException;
import object.Aluno;
import object.DadosPessoais;
import object.Endereco;
import object.Telefone;

public class AlunoDAOTest {

	@Test
	public void testAdiciona() {
		fail("tudo ok");
		AlunoDAO dao = new AlunoDAO();
		//System.out.println(dao.existeAluno("111111"));	
		Aluno a = new Aluno();
		a.setMatricula("19970101");
		a.setAno("2017");
		DadosPessoais d = new DadosPessoais("Herculano", "06/02/1997","M", "100288322183","3029192",null,null, "herculano@gmail.com",null);
		Endereco e = new Endereco("SP", "NITEROI", "travessa cangeuiro", "CENTRO", 10010,"PERTO DA PADARIA DO SEU ZE");
		Telefone t = new Telefone("Tim", "83", "991717171");
		
		d.setEndereco(e);
		d.setTelefone(t);
		a.setDadosPessoais(d);
		
		try {
			dao.adiciona(a);
		} catch (ErroConexaoException | AlunoJaExisteException ex) {
			fail(ex.getMessage());
			ex.printStackTrace();
		}
		
		
	}

	@Test
	public void testAltera() {
		fail("tudo ok");
		AlunoDAO dao = new AlunoDAO();
		Aluno a = new Aluno();
		a.setMatricula("19970101");
		a.setAno("2001");
		DadosPessoais d = new DadosPessoais("Syslan", "06/02/1997","M", "092983210","3029192",null,null, "syslan@gmail.com",null);
		Endereco e = new Endereco("PB", "MATARACA", "RUA DO FIO", "MATA", 10010,"DA CASA DE ABNER");
		Telefone t = new Telefone("Tim", "83", "99999");
		
		d.setEndereco(e);
		d.setTelefone(t);
		a.setDadosPessoais(d);
		
		try {
			dao.altera(a, "100288322183");
		} catch (ErroConexaoException | AlunoInexistenteException eX) {
			fail(eX.getMessage());
			eX.printStackTrace();
		}
		
	}

	@Test
	public void testRemove() {
		fail("TUDO OK");
		AlunoDAO dao = new AlunoDAO();
		Aluno a = new Aluno();
		a.setMatricula("19970101");
		try {
			dao.remove("092983210");
		} catch (ErroConexaoException | AlunoInexistenteException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPesquisaId() {
		fail("TUDO OK");
		AlunoDAO sis = new AlunoDAO();
		try {
			System.out.println(sis.pesquisaId("100288322183"));
		} catch (ErroConexaoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testPesquisa() {
		fail("TUDO OK");
		AlunoDAO sis = new AlunoDAO();
		try {
			System.out.println(sis.pesquisa("092983210").toString());
			sis.pesquisa("092983210");
			
			//System.out.println(sis.existeAluno("19970101"));
			
			//System.out.println(
			//		sis.pesquisaCpf("19970101") +";"); //pesquisaCpf
		} catch (ErroConexaoException | AlunoInexistenteException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	@Test
	public void testGetLista() {
		AlunoDAO sis = new AlunoDAO();
		
		List<Aluno> l;
		try {
			l = sis.getLista();
			
			
			System.out.println(l.get(0).toString());
			
			
		} catch (ListaVaziaException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void nomeTelefoneEmail() {
		AlunoDAO sis = new AlunoDAO();
		
		try {
			String[][] s =sis.nomeTelefoneEmail(29);
			for(int i=0;i<s.length;i++) {
				System.out.println(s[i][0]);
				System.out.println(s[i][1]);
				System.out.println(s[i][2]);
			}
		} catch (ErroConexaoException e) {
			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
