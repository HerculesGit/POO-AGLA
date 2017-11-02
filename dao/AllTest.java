package dao;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import exception.AlunoInexistenteException;
import exception.ErroConexaoException;
import exception.ProfessorInexistenteException;
import object.Aluno;
import object.Professor;

public class AllTest {

	@Test
	public void test() {
		AlunoDAO dao = new AlunoDAO();
		DisciplinasDAO disc = new DisciplinasDAO();
		ProfessorDAO pdao = new ProfessorDAO();
		
		
		try {
			Professor p = (Professor) pdao.pesquisaNome("Herculano");
			System.out.println(p.toString());
		} catch (ErroConexaoException | ProfessorInexistenteException e) {
			
			e.printStackTrace();
		}
		
//		
//		try {
//			Aluno a = (Aluno) dao.pesquisaNome("Herculano");
//			
//			System.out.println(a.toString());
//			
//			System.out.println(" --------- ");
//			
//			System.out.println(dao.pesquisaPrimeiroNome("Herculano"));
//			
//			System.out.println(" --------- ");
//			
//
//			
//		} catch (ErroConexaoException | AlunoInexistenteException e) {
//			
//			e.printStackTrace();
//		}
		Scanner scan = new Scanner(System.in);
		try {
			for(int i=0;i<10;i++) {
				String letra = scan.nextLine();
				
				String palavra = dao.pesquisaNomeLetra(letra.charAt(letra.length()-1), letra);
				
				
				System.out.println(palavra);
			}
			scan.close();
//			String palavra = dao.pesquisaNomeLetra('l', "h");
//			palavra = dao.pesquisaNomeLetra('l', "hercu");
//			palavra = dao.pesquisaNomeLetra('l', "hercu");
//			palavra = dao.pesquisaNomeLetra('l', "hercu");
//			palavra = dao.pesquisaNomeLetra('l', "hercu");
			
			
//			System.out.println(palavra);
		} catch (ErroConexaoException e) {
			
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		
//		try {
//			Aluno a = dao.pesquisa1("19970101");
//			System.out.println(a.toString());
//			
//		} catch (ErroConexaoException | AlunoInexistenteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
		
//		try {
//			Aluno a = dao.pesquisa("100288322183");
//			System.out.println(a.toString());
//			
//		} catch (ErroConexaoException | AlunoInexistenteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//		
		
//		try {
//			System.out.println(dao.pesquisaCpf("19970101"));
//		} catch (ErroConexaoException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
		
		
//		DisciplinasDAO d = new DisciplinasDAO();
//		
//		try {
//			String[][] s = d.professorLeciona("101010");
//			
//			for(int i=0;i<s.length;i++) {
//				System.out.println(s[i][0]);
//			}
//			
//			
//		} catch (ErroConexaoException | ProfessorInexistenteException e) {
//			
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
		
		
		
//		
//		try {
//			
//			String s[][] = disc.historico(29, 1);
//			
//			System.out.println("-> " + s.length);
////			
//			System.out.print(s[0][0] + " | ");
//			System.out.print(s[0][1] + " | ");
//			System.out.print(s[0][2] + " | ");
//			
//			System.out.print(s[0][3] + " | ");
//			System.out.print(s[0][4] + " | ");
//			System.out.print(s[0][5] + " | ");
//			System.out.println(s[0][6]);
			
//			for (int i = 0; i < s.length; i++) {
//				
//				System.out.print(s[i][0] + " | ");
//				System.out.print(s[i][1] + " | ");
//				System.out.print(s[i][2] + " | ");
//
//				System.out.print(s[i][3] + " | ");
//				System.out.print(s[i][4] + " | ");
//				System.out.print(s[i][5] + " | ");
//				System.out.print(s[i][6] + " | ");
//				System.out.println(s[i][7]);
//			}
//			
//			//System.out.print(s[1][1] + " |- ");
//			
//			
//		} catch (ErroConexaoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			//fail(e.getMessage());
//		}
		
		
		
	}

}
