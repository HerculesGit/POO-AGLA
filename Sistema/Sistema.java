package Sistema;
import exception.*;
import java.util.*;

import dao.AlunoDAO;
import dao.EntraDAO;
import dao.ProfessorDAO;

public class Sistema {
	
	private ProfessorDAO pdao;
	private AlunoDAO adao;
	private EntraDAO edao;
	
    Map<String, Professor> professores;
    Map<String, Aluno> alunos;
    
    public Sistema() {
        this(new HashMap<String, Professor>(), new HashMap<String, Aluno>());
    }

    public Sistema(Map<String, Professor> professores, Map<String, Aluno> alunos) {
        this.professores = professores;
        this.alunos = alunos;
        this.pdao = new ProfessorDAO();
        this.adao = new AlunoDAO();
        this.edao = new EntraDAO();
    }

    public void setProfessores(Map<String, Professor> professores) {
        this.professores = professores;
    }

    public Map<String, Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Map<String, Aluno> alunos) {
        this.alunos = alunos;
    }

    public void addAluno(String cpf, Aluno aluno) throws JaExisteException {
        
    	try {
    		adao.adiciona(aluno);
		} catch (ErroConexaoException e) {
			throw new JaExisteException("Aluno <"+aluno.getDadosPessoais().getCpf()+"> ja cadastrado no sistema");
		}
    }

    public void addProfessor(String cpf, Professor professor) throws JaExisteException {
        
    	try {
			pdao.adiciona(professor);
		} catch (ErroConexaoException e) {
			 throw new JaExisteException("Professor <"+professor.getDadosPessoais().getCpf()+"> ja cadastrado no sistema");
		}	
    }

    public void removeAluno(String cpf) throws NaoExisteException {
    	
    	try {
    		adao.remove(cpf);
		} catch (ErroConexaoException e) {
			throw new NaoExisteException("Erro ao alterar. Aluno <"+cpf+"> nao encontrado no banco de dados");
		}
    }

    public void removeProfessor(String cpf) throws NaoExisteException {
    	
    	try {
    		pdao.remove(cpf);
		} catch (ErroConexaoException e) {
			throw new NaoExisteException("Erro ao remover professor. Professor <"+cpf+"> nao encontrado no banco de dados");
		}
    }

    public Aluno verificarAluno(String nome) throws NaoExisteException {
        Aluno a = new Aluno();
        try {
			a = (Aluno) adao.pesquisaNome(nome);
		} catch (ErroConexaoException e) {
			throw new NaoExisteException("Aluno <"+nome+"> nao encontrado no banco de dados");
		}
        
        return a;
    }

    public Professor verificarProfessor(String cpf) throws NaoExisteException {
        Professor p = new Professor();
        
		try {
			p=pdao.pesquisa(cpf);
		} catch (ErroConexaoException e) {
			throw new NaoExisteException("Professor <"+cpf+"> nao encontrado no banco de dados");
		}
		return p;
    }

    public List<Aluno> verificarTurma(String ano) throws TurmaInexistenteExeption {
    	/** Retornar um array
    	 * 
    	 * 
    	 * 
    	 * 
    	 */
        List<Aluno> lista = new ArrayList<Aluno>();
        for (Aluno a : alunos.values()) {
            if (a.getAno().equals(ano)) {
                lista.add(a);
            }
        }
        if(lista.size() == 0){
            throw new TurmaInexistenteExeption("Esta turma não foi cadastrada");
        }
        return lista;
    }

    //@SuppressWarnings("unlikely-arg-type")
	public List<Professor> professoresDaDisciplina(String nomeDisciplina) throws NaoExisteException {
        List<Professor> lista = new ArrayList<Professor>();
        for (Professor p : getListaDisciplinas()) {
//            if (p.getDisciplinas().contains(nomeDisciplina)) {
//                lista.add(p);
//            }
        }
        if(lista.size() == 0){
            throw new NaoExisteException("Não exite professores para esta disciplina");
        }
        return lista;
    }
	
	public void alterarProfessor(Professor professor) throws NaoExisteException{
		try {
			pdao.altera(professor, professor.getDadosPessoais().getCpf());
			
			//TODO: Arrumar o cpf depois!!
		} catch (ErroConexaoException e) {
			throw new NaoExisteException("Professor não encontrado.");
		}
	}	
	public void alterarAluno(Aluno aluno) throws NaoExisteException{
		try {
			adao.altera(aluno, aluno.getDadosPessoais().getCpf());
			
			//TODO: Arrumar o cpf depois!!
		} catch (ErroConexaoException e) {
			throw new NaoExisteException("Professor não encontrado.");
		}
	}
	
	public boolean entra(String login, String senha) throws NaoExisteException{
		try {
			if(edao.entra(login, senha)) return true;
		} catch (ErroConexaoException e) {
			e.printStackTrace();
		} catch(NaoExisteException e2) {
			//e2.printStackTrace();
		}
		return false;
	}
	
	public String[][] getListaProf(){
		try {
			return pdao.getLista();
		} catch (ListaVaziaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<Professor> getListaDisciplinas(){
		try {
			return pdao.getListaProf();
		} catch (ListaVaziaException e) {}
		return null;
	}
}
