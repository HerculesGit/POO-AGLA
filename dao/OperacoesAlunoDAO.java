package dao;
import java.util.List;

import exception.*;
import Sistema.*;

public interface OperacoesAlunoDAO {
	
	/**
	 * @param aluno eh o aluno com as informacoes a ser alterada
	 * @throws ErroConexaoException
	 * @throws AlunoJaExisteException
	 * */
	public void adiciona(Aluno aluno)  throws ErroConexaoException, JaExisteException ;
	
	
	/**
	 * O metodo ira pesquisar a existencia do cpf
	 * @param cpf cpf do aluno a remover
	 * @throws ErroConexaoException
	 * @throws AlunoInexistenteException
	 * */
	public void remove(String cpf)  throws ErroConexaoException, NaoExisteException ;
	
	
	/**
	 * O metodo precisa que ele tenha cpf informado
	 * Informando o cpf, assim podera mudar o cpf atual que estara com o objeto aluno
	 * @param aluno Um objeto com todas as informacoes para serem alteradas
 	 * @param cpf Uma String com o cpf existente
	 * @throws ErroConexaoException
	 * @throws AlunoInexistenteException
	 * */
	public void altera(Aluno aluno, String cpf) throws ErroConexaoException, NaoExisteException ;
	
	/**
	 * O metodo precisa que ele tenha cpf informado
	 * Informando o cpf, assim podera mudar o cpf atual que estara com o objeto aluno
 	 * @param cpf Uma String com o cpf existente
	 * @throws ErroConexaoException Caso nao consiga se conectar com o banco de dados
	 * @throws AlunoInexistenteException Caso nao exista um aluno no banco de dados
	 * */
	public Aluno pesquisa(String cpf)  throws ErroConexaoException, NaoExisteException;
	
	
	/**
	 * O metodo requer a matricula de um aluno e retorna true caso ache e false quando nao achar
	 * @param matricula Um String que eh a matricula do aluno
	 * @throws ErroConexaoException Caso nao consiga se conectar com o banco de dados
	 * 
	 * */
	public boolean existe(String matricula) throws ErroConexaoException ;
	
	/**
	 * Um array list com a todos os alunos de alunos
	 * @return List a lista de alunos cadastradas no sistema
	 * @throws ListaVaziaException
	 * */
	public List<Aluno> getLista() throws ListaVaziaException;
	
	
}