package dao;

import static org.junit.Assert.*;

import org.junit.Test;

import exception.ErroConexaoException;
import exception.LoginJaExisteException;

public class EntraDAOTest {

	@Test
	public void testAdiciona() {
		EntraDAO e = new EntraDAO();
		
			try {
				e.adiciona("ekba56", "123");
			} catch (ErroConexaoException | LoginJaExisteException e1) {
				fail(e1.getMessage());
			}
		
	}

	@Test
	public void testEntra() {
		EntraDAO e = new EntraDAO();
		
		try {
			System.out.println(e.entra("123456", "23"));
		} catch (ErroConexaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
