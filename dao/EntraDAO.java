package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.*;

public class EntraDAO {
	private final String ENTRA = "Entra";
	private Connection connection;
	public EntraDAO(){
		try {
			connection = new ConnectionFactory().getConnection();
		} catch (SQLException e) {
			
		}
	}
	
	public void adiciona(String login, String senha) throws ErroConexaoException, LoginJaExisteException {
		if(existe(login)){
			throw new LoginJaExisteException("Login ja cadastrado");
		}
		
		String sql = "INSERT INTO "+this.ENTRA+"(login,senha) VALUES(?,?)";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, login);
			pstmt.setString(2, senha);

			pstmt.execute();

		} catch(SQLException ex) {
			throw new ErroConexaoException("Erro salvar login e senha");
		}
	}
	
	public boolean entra(String login, String senha) throws ErroConexaoException, NaoExisteException{
		String sql = "SELECT login,senha FROM "+this.ENTRA+" WHERE login=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){

			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			pstmt.executeQuery();
			
			if(rs.next()){
				if(senha.equals(rs.getString("senha"))){
					return true;
				}
			}
		} catch (SQLException e) {
			throw new ErroConexaoException("Erro ao tentar verificar login senha");
		}
		
		throw new NaoExisteException("Login ou senha incorreta");
	}
	
	private boolean existe(String login) throws ErroConexaoException{
		String sql = "SELECT login FROM "+this.ENTRA + " WHERE login=?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				if(login.equals(rs.getString("login"))){
					return true;
				}
			}
			
		} catch(SQLException ex) {
			throw new ErroConexaoException("Erro salvar login e senha");
		}
		
		return false;
	}	
	
} // end class