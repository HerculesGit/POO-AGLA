package dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import exception.ErroConexaoException;
import exception.ListaVaziaException;
import exception.ProfessorInexistenteException;
import exception.ProfessorJaExisteException;
import object.DadosPessoais;
import object.Endereco;
import object.Professor;
import object.Telefone;

public class ProfessorDAOTest {

	@Test
	public void testAdiciona() {
		fail("TUDO OK");fail("Not yet implemented");
		
		ProfessorDAO dao = new ProfessorDAO();
		//System.out.println(dao.existeAluno("111111"));	
		Professor a = new Professor();
		a.setMatricula("19970101");
		a.setLattes("http:// ");
		DadosPessoais d = new DadosPessoais("Herculano", "06/02/1997","M", "100288322183","3029192",null,null, "herculano@gmail.com",null);
		Endereco e = new Endereco("SP", "NITEROI", "travessa cangeuiro", "CENTRO", 10010,"PERTO DA PADARIA DO SEU ZE");
		Telefone t = new Telefone("Tim", "83", "991717171");
		
		d.setEndereco(e);
		d.setTelefone(t);
		a.setDadosPessoais(d);
		
		try {
			dao.adiciona(a);
		} catch (ErroConexaoException | ProfessorJaExisteException e1) {
			fail(e1.getMessage());
			e1.printStackTrace();
		}
		
	}

	@Test
	public void testAltera() {
		fail("TUDO OK");
		ProfessorDAO dao = new ProfessorDAO();
		Professor a = new Professor();
		a.setMatricula("19970101");
		a.setLattes("http://s/s/s/s");
		DadosPessoais d = new DadosPessoais("Syslan", "06/02/1997","M", "092983210","3029192",null,null, "syslan@gmail.com",null);
		Endereco e = new Endereco("PB", "MATARACA", "RUA DO FIO", "MATA", 10010,"DA CASA DE ABNER");
		Telefone t = new Telefone("Tim", "83", "99999");
		
		d.setEndereco(e);
		d.setTelefone(t);
		a.setDadosPessoais(d);

		try {
			dao.altera(a, "100288322183");
		} catch (ErroConexaoException | ProfessorInexistenteException e1) {
			fail(e1.getMessage());
			e1.printStackTrace();
		}
	}

	@Test
	public void testRemove() {
		fail("TUDO OK");
		ProfessorDAO dao = new ProfessorDAO();
		
		try {
			dao.remove("092983210");
		} catch (ErroConexaoException | ProfessorInexistenteException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testPesquisa() {
		fail("TUDO OK");
		ProfessorDAO dao = new ProfessorDAO();
		Professor p = null;
		try {
			p = dao.pesquisa("100288322183");
		} catch (ErroConexaoException | ProfessorInexistenteException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(p.toString());
		
	}

	@Test
	public void testExisteProfessor() {
		//fail("TUDO OK");
		ProfessorDAO dao = new ProfessorDAO();
		try {
			System.out.println(dao.existeProfessor("201603"));
		} catch (ErroConexaoException e) {
			
			e.printStackTrace();
		}
	}

	@Test
	public void testPesquisaCpf() {
		//fail("TUDO OK");
		ProfessorDAO dao = new ProfessorDAO();
		try {
			System.out.println(dao.pesquisaCpf("101010"));
		} catch (ErroConexaoException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPesquisaId() {
		fail("TUDO OK");
		ProfessorDAO dao = new ProfessorDAO();
		try {
			System.out.println(dao.pesquisaId("100288322183"));
		} catch (ErroConexaoException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetLista() {
		ProfessorDAO dao = new ProfessorDAO();
		try {
			List<Professor> l = dao.getLista();
			
			System.out.println(l.get(0).toString());
			
		} catch (ListaVaziaException e) {
			
			
		}
	}

}
