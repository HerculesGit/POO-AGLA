package dao;

import exception.JaExisteException;
import exception.ErroConexaoException;
import Sistema.Disciplina;

public interface OperacoesDisciplinasDAO {
	
	
	public void adiciona(Disciplina disciplina) throws ErroConexaoException, JaExisteException;
	
	public void remove() throws ErroConexaoException;
	
	public Disciplina pesquisa() throws ErroConexaoException;
	
	public boolean existe() throws ErroConexaoException;
	
}
