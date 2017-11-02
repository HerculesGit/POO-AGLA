package dao;

import java.util.List;

import exception.*;
import Sistema.Professor;

public interface OperacoesProfessorDAO {
	
	
	/**
	 * @param professor eh o professor com as informacoes a ser alterada
	 * @throws ErroConexaoException
	 * @throws AlunoJaExisteException
	 * */
	public void adiciona(Professor professor)  throws ErroConexaoException, JaExisteException ;
	
	
	/**
	 * O metodo ira pesquisar a existencia do cpf
	 * @param cpf cpf do aluno a remover
	 * @throws ErroConexaoException
	 * @throws AlunoInexistenteException
	 * */
	public void remove(String cpf)  throws ErroConexaoException, NaoExisteException ;
	
	
	/**
	 * O metodo precisa que ele tenha cpf informado
	 * Informando o cpf, assim podera mudar o cpf atual que estara com o objeto professor
	 * @param aluno
 	 * @param cpf 
	 * @throws ErroConexaoException
	 * @throws AlunoInexistenteException
	 * */
	public void altera(Professor professor, String cpf) throws ErroConexaoException, NaoExisteException ;
	
	
	/**
	 * O metodo precisa que ele tenha cpf informado
	 * Informando o cpf, assim podera mudar o cpf atual que estara com o objeto professor
 	 * @param cpf 
	 * @throws ErroConexaoException
	 * @throws AlunoInexistenteException
	 * */
	public Professor pesquisa(String cpf)  throws ErroConexaoException, NaoExisteException;
	
	
	/**
	 * O metodo requer a matricula de um professor e retorna true caso ache e false quando nao achar
	 * @param matricula Um String que eh a matricula do professor
	 * @throws ErroConexaoException Caso nao consiga se conectar com o banco de dados
	 * 
	 * */
	public boolean existe(String matricula) throws ErroConexaoException ;
	
	/**
	 * @return List a lista de professores cadastradas no sistema
	 * @throws ListaVaziaException 
	 * */
	public List<Professor> getLista() throws ListaVaziaException;
	
	
}
