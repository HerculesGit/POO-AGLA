package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.*;
import Sistema.*;

public class ProfessorDAO implements OperacoesProfessorDAO{
    private Connection connection;
 
    // definicao de constantes de acesso a tabelas
    private final String PROFESSOR = "Professor";
    private final String DADOS_PESSOAIS_PROFESSOR = "DadosPessoaisProfessor";
    private final String TELEFONE_PROFESSOR = "TelefoneProfessor";
    private final String ENDERECO_PROFESSOR = "EnderecoProfessor";
 
 
    public ProfessorDAO() {
        try {
            connection = new ConnectionFactory().getConnection();
        } catch (SQLException e) {
            System.out.println("*/*/");
        }
    }
 
    @Override
    public void adiciona(Professor professor) throws ErroConexaoException, JaExisteException {
        int id=0;
        id = existeProfessor(professor.getMatricula());
 
        if(id==0) {
 
            String sql = "INSERT INTO "+this.PROFESSOR+"(matricula,lattes) VALUES(?,?)";
 
            try(PreparedStatement pstmt = connection.prepareStatement(sql)){
                pstmt.setString(1, professor.getMatricula());
                pstmt.setString(2, professor.getLattes());
 
                pstmt.execute();
 
            } catch(SQLException ex) {
                throw new ErroConexaoException("Erro na conecao tentar salvar professor");
            }
            id = existeProfessor(professor.getMatricula());
            ///nome,sexo,cidade,logradouro,numero,bairro,complemento,id_professor
 
            String sqlD = "INSERT INTO "+this.DADOS_PESSOAIS_PROFESSOR+"(nome,sexo,rg,cpf,dataNascimento,email,id_professor)"
                    + " VALUES (?,?,?,?,?,?,?)";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlD)){
 
                pstmt.setString(1, professor.getDadosPessoais().getNome());             // nome
                pstmt.setString(2, professor.getDadosPessoais().getSexo());             // sexo
                pstmt.setString(3, professor.getDadosPessoais().getRg());               // rg
                pstmt.setString(4, professor.getDadosPessoais().getCpf());              // cpf
                pstmt.setString(5, professor.getDadosPessoais().getDataNascimento());   // dataNascimento
                pstmt.setString(6, professor.getDadosPessoais().getEmail());            // email
                pstmt.setInt(7, id);                                                    // id_professor
 
                pstmt.execute();
 
            } catch (SQLException e) {
                throw new ErroConexaoException("Erro na conecao tentar salvar dados do professor");
            }
             
            String sqlE = "INSERT INTO "+this.ENDERECO_PROFESSOR+"(estado,cidade,logradouro,numero,bairro,complemento,id_professor) "
                    + "VALUES(?,?,?,?,?,?,?)";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlE)){
                pstmt.setString(1, professor.getDadosPessoais().getEndereco().getEstado());         // entado
                pstmt.setString(2, professor.getDadosPessoais().getEndereco().getCidade());         // cidade
                pstmt.setString(3, professor.getDadosPessoais().getEndereco().getLogradouro());     // logradouro
                pstmt.setInt(4, professor.getDadosPessoais().getEndereco().getNumero());            // numero
                pstmt.setString(5, professor.getDadosPessoais().getEndereco().getBairro());         // bairro
                pstmt.setString(6, professor.getDadosPessoais().getEndereco().getComplemento());    // complemento
                pstmt.setInt(7, id);                                                                // id_professor
 
                pstmt.execute();
 
            } catch(SQLException ex){
                throw new ErroConexaoException("Erro ao tentar salvar endereco do professor");
            }
 
            String sqlT = "INSERT INTO "+this.TELEFONE_PROFESSOR+"(operadora,prefixo,numero,id_professor) "
                    + "VALUES(?,?,?,?)";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlT)){
                pstmt.setString(1, professor.getDadosPessoais().getTelefone().getOperadora());  // operadora
                pstmt.setString(2, professor.getDadosPessoais().getTelefone().getPrefixo());    // prefixo
                pstmt.setString(3, professor.getDadosPessoais().getTelefone().getNumero());     // numero
                pstmt.setInt(4, id);                                                        // id_professor
 
                pstmt.execute();
                return;
            } catch (SQLException ex) {
                throw new ErroConexaoException("Erro ao tentar salvar telefone do professor");
            }
        }
        throw new JaExisteException("professor ja adicionado no sistema");
 
    }
 
    @Override
    public void altera(Professor professor, String cpf) throws ErroConexaoException, NaoExisteException {
        int id = 0;
        id = pesquisaId(cpf);
        //System.out.println(id);
        // Se ele existir entao pode alterar
        if(id>0) {
            String sql = "UPDATE "+this.PROFESSOR+" SET matricula=?, lattes=? WHERE idProfessor=?";
            System.out.println(sql);
            try(PreparedStatement pstmt = connection.prepareStatement(sql)){
 
                pstmt.setString(1, professor.getMatricula());       // matricula
                pstmt.setString(2, professor.getLattes());          // lattes
                pstmt.setInt(3, id);                                // id continua sendo o mesmo
 
                pstmt.executeUpdate();
 
            } catch (SQLException e) {
                throw new ErroConexaoException("Erro ao tentar alterar alguns dados do professor");
            }
 
            // update em Telefone
            String sqlT = "UPDATE "+this.TELEFONE_PROFESSOR+" SET operadora=?, prefixo=?, numero=? WHERE id_professor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlT)){
                pstmt.setString(1, professor.getDadosPessoais().getTelefone().getOperadora());      // operadora
                pstmt.setString(2, professor.getDadosPessoais().getTelefone().getPrefixo());        // prefixo
                pstmt.setString(3, professor.getDadosPessoais().getTelefone().getNumero());         // numero
 
                pstmt.setInt(4, id);                                                                // id continua sendo o mesmo
 
                pstmt.executeUpdate();                                                              // executar acao
 
            } catch (SQLException e) {
                throw new ErroConexaoException("Erro ao tentar alterar telefone ");
            }
 
            // update em Endereco
            String sqlE = "UPDATE "+this.ENDERECO_PROFESSOR+" SET estado=?, cidade=?, logradouro=?, numero=?, bairro=?, complemento=? WHERE id_professor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlE)){
                pstmt.setString(1, professor.getDadosPessoais().getEndereco().getEstado());                 // estado
                pstmt.setString(2, professor.getDadosPessoais().getEndereco().getCidade());                 // cidade
                pstmt.setString(3, professor.getDadosPessoais().getEndereco().getLogradouro());             // logradouro
                pstmt.setInt(4, professor.getDadosPessoais().getEndereco().getNumero());                    // numero
                pstmt.setString(5, professor.getDadosPessoais().getEndereco().getBairro());                 // bairro
                pstmt.setString(6, professor.getDadosPessoais().getEndereco().getComplemento());            // complemento
 
                pstmt.setInt(7, id);                                                                        // id continua sendo o mesmo
 
                pstmt.executeUpdate();                                                                      // executar acao
 
            } catch (SQLException e) {
                throw new ErroConexaoException("Erro ao tentar alterar endereco do professor");
            }
 
            // update em DadosPessoais
            String sqlD = "UPDATE "+this.DADOS_PESSOAIS_PROFESSOR+" SET nome=?, sexo=?, rg=?, cpf=?, dataNascimento=?, email=?  WHERE id_professor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlD)){
                pstmt.setString(1, professor.getDadosPessoais().getNome());             // nome
                pstmt.setString(2, professor.getDadosPessoais().getSexo());             // sexo
                pstmt.setString(3, professor.getDadosPessoais().getRg());               // rg
                pstmt.setString(4, professor.getDadosPessoais().getCpf());              // cpf
                pstmt.setString(5, professor.getDadosPessoais().getDataNascimento());   // data nascimento
                pstmt.setString(6, professor.getDadosPessoais().getEmail());            // email
 
                pstmt.setInt(7, id);                                                    // id continua sendo o mesmo
 
                pstmt.executeUpdate();                          // executar acao
                return;                                         // sair do metodo
            } catch (SQLException e) {
                throw new ErroConexaoException("Erro ao tentar alterar dados pessoais professor");
            }
        } // if
        throw new NaoExisteException("professor nao encontrado no banco de dados");
 
    }
 
    @Override
    public void remove(String cpf) throws ErroConexaoException, NaoExisteException {
        int id=0;
        id = pesquisaId(cpf);
        // isso quer dizer que ele nao existe, e nao tem como remover algo que nao existe
        if(id==0) {
            throw new NaoExisteException("Impossivel alterar. Professor nao encontrado");
        }
 
        // deletar telefone
        String sqlT = "DELETE FROM "+this.TELEFONE_PROFESSOR+" WHERE id_professor=?";
        try(PreparedStatement pstmt  = connection.prepareStatement(sqlT)){
            pstmt.setInt(1, id);
            pstmt.execute();
        }catch (SQLException e) {
            throw new ErroConexaoException("Erro ao tentar remover telefone do professor");
        }
 
        // deletar endereco
        String sqlE = "DELETE FROM "+this.ENDERECO_PROFESSOR+" WHERE id_professor="+id;
        try(PreparedStatement pstmt  = connection.prepareStatement(sqlE)){
            pstmt.execute();
        }catch (SQLException e) {
            throw new ErroConexaoException("Erro ao tentar remover endereco do professor");
        }
 
        // deletar dados pessoais
        String sqlD = "DELETE FROM "+this.DADOS_PESSOAIS_PROFESSOR+" WHERE id_professor=?";
        try(PreparedStatement pstmt  = connection.prepareStatement(sqlD)){
            pstmt.setInt(1, id);
            pstmt.execute();
        }catch (SQLException e) {
            throw new ErroConexaoException("Erro ao tentar remover endereco do professor");
        }
 
        // deletar o professor. Tem que ser por ultimo
        String sql = "DELETE FROM "+this.PROFESSOR+" WHERE idProfessor=?";
        try(PreparedStatement pstmt  = connection.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new ErroConexaoException("Erro ao tentar remover professor");
        }
 
    }
 
     
    @Override
    public Professor pesquisa(String cpf) throws ErroConexaoException, NaoExisteException {
        int id=0;
        id = pesquisaId(cpf);
        System.out.println(id);
        if(id>0) {
            System.out.println(">");
            Professor professor = new Professor();
            DadosPessoais dados = new DadosPessoais();
            Telefone tel = new Telefone();
            Endereco endereco = new Endereco();
 
            // pesquisa telefone
            String sqlT = "SELECT * FROM "+this.TELEFONE_PROFESSOR+" WHERE id_professor=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlT)){
 
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
 
                if(rs.next()){
                    tel.setId(rs.getInt("idTelefone"));
                    tel.setOperadora(rs.getString("operadora"));
                    tel.setPrefixo(rs.getString("prefixo"));
                    tel.setNumero(rs.getString("numero"));
                }
                rs.close();
            } catch (SQLException e){
                throw new ErroConexaoException("Erro ao tentar recuperar telefone, possivelmente um erro ao tentar se conectar com o banco de dados");
            }
 
            // pesquisa Endereco
            String sqlE = "SELECT * FROM "+this.ENDERECO_PROFESSOR+" WHERE id_professor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlE)){
 
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
 
                if (rs.next()) {
                    endereco.setId(rs.getInt("idEndereco"));
                    endereco.setEstado(rs.getString("estado"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setLogradouro(rs.getString("logradouro"));
                    endereco.setNumero(rs.getInt("numero"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setComplemento(rs.getString("complemento"));
                }
                rs.close();             // fechando rs
            } catch (SQLException e){
                throw new ErroConexaoException("Erro ao tentar recuperar dados pessoais, possivelmente um erro ao tentar se conectar com o banco de dados");
            }
            // TODO
            // pesquisa DadosPessois
            String sqlD = "SELECT * FROM "+this.DADOS_PESSOAIS_PROFESSOR+" WHERE id_professor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlD)){
 
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
 
                // Se houver dados - OK
                if(rs.next()) {
                    dados.setId(rs.getInt("idDadosPessoais"));
                    dados.setNome(rs.getString("nome"));
                    dados.setDataNascimento(rs.getString("dataNascimento"));
                    dados.setSexo(rs.getString("sexo"));
                    dados.setRg(rs.getString("rg"));
                    dados.setCpf(rs.getString("cpf"));
                    dados.setEmail(rs.getString("email"));
                }
                rs.close();         // fechando rs
            } catch (SQLException ex){
                throw new ErroConexaoException("Erro ao recuperar dados pessoais");
            }
             
             
            // pesquisando professor -ok
            String sql = "SELECT * FROM "+this.PROFESSOR+" WHERE idProfessor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sql)){
 
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()) {
                    professor.setId(rs.getInt("idProfessor"));          // idProfessor
                    professor.setMatricula(rs.getString("matricula"));  // matricula
                    professor.setLattes(rs.getString("lattes"));        // lattes
                }
                rs.close();                                         // fechando rs
            }catch (SQLException ex){
                throw new ErroConexaoException("Erro ao recuperar dados pessoais");
            }
 
            dados.setEndereco(endereco);
            dados.setTelefone(tel);
 
 
            professor.setDadosPessoais(dados);
 
            //System.out.println(tel.toString());
 
            return professor;
        } // if
        throw new NaoExisteException("Professor nao encontrado no banco de dados");
    }
 
 
    // Sera private - id
    /**Retorna o id*/
    public int existeProfessor(String matricula) throws ErroConexaoException {
         
        StringBuilder sqlQuery = new StringBuilder().append("SELECT idProfessor FROM ")
                .append(this.PROFESSOR)
                .append(" WHERE matricula=?");
         
        System.out.println(sqlQuery);
        try(PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())){
 
            pstmt.setString(1, matricula);          // colocar como segundo parametro
                                                    // o que esta em interrogacao
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("idProfessor");
            }
            rs.close();                             // fechando conexao
        } catch(SQLException ex){
            throw new ErroConexaoException("Erro ao tentar pesquisar professor.");
        }
        return 0;
    }
 
 
    // Sera private - cpf
    /**Pesquisa por matricula e Retorna o cpf*/
    public String pesquisaCpf(String matricula) throws ErroConexaoException{
        int id=0;
        id = existeProfessor(matricula);
        if(id>0) {
            String sql = "SELECT cpf FROM "+this.DADOS_PESSOAIS_PROFESSOR+" WHERE id_professor=?";
 
            try(PreparedStatement pstmt = connection.prepareStatement(sql)){
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()) {
                    return rs.getString("cpf");
                }
                rs.close();                     // fechando rs
            } catch(SQLException ex){
                throw new ErroConexaoException("Erro ao tentar pesquisar professor.");
            }
        }
        return "";
    }
 
 
    /**Pesquisa por cpf e Retorna o id*/
    public int pesquisaId(String cpf) throws ErroConexaoException {
        String sql = "SELECT id_professor FROM "+this.DADOS_PESSOAIS_PROFESSOR+" WHERE cpf=?"; //"+cpf;
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, cpf);
 
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("id_professor");
            }
            rs.close();                     // fechando rs
        }catch(SQLException ex){
            throw new ErroConexaoException("Erro ao tentar pesquisar professor.");
        }
        return 0;
    }
     
    // TODO: Novo metodo
    /** 
     * Pesquisa por cpf e retorna a matricula
     * @param cpf
     * @return String vazio senao houver matricula se houver
     * 
     * */
    public String pesquisaMatricula(String cpf) throws ErroConexaoException{
        int id = pesquisaId(cpf);
        if(id>0) {
            String sqlP = "SELECT matricula FROM "+this.PROFESSOR+" WHERE idProfessor=?"; //"+cpf;
            try(PreparedStatement pstmt = connection.prepareStatement(sqlP)){
                pstmt.setInt(1, id);
                 
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()) {
                    return rs.getString("matricula");
                }
                rs.close();                     // fechando rs
            }catch(SQLException ex){
                throw new ErroConexaoException("Erro ao tentar pesquisar aluno.");
            }
        }
        return "";
    }
     
 
    @Override
    public boolean existe(String matricula) throws ErroConexaoException {
        return existeProfessor(matricula) > 0;
    }
 
    // TODO: Tentar fazer isso aqui
    @Override
    public List<Professor> getLista() throws ListaVaziaException {
        List<Professor> temp = new ArrayList<Professor>();
         
        // PROFESSOR
        String slqP = "SELECT * FROM "+this.PROFESSOR;
        try(PreparedStatement pstmt = connection.prepareStatement(slqP);
            ResultSet rs = pstmt.executeQuery()){
             
            while(rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt("idProfessor"));          // idProfessor
                p.setMatricula(rs.getString("matricula"));  // matricula
                p.setLattes(rs.getString("lattes"));        // lattes
                temp.add(p);
            }
             
        }catch (SQLException ex){
             
        }
         
        if(temp.size() == 0) {
            throw new ListaVaziaException("Nenhum aluno encontrado");
        }
         
        int i = 0;
         
        while(i<temp.size()) {
            int id = temp.get(i).getId();
             
            // Professor
            String sqlD = "SELECT * FROM "+this.DADOS_PESSOAIS_PROFESSOR+" WHERE id_professor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlD)){
 
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                 
                if(rs.next()) {
                    temp.get(i).getDadosPessoais().setId(rs.getInt("idDadosPessoais"));
                    temp.get(i).getDadosPessoais().setNome(rs.getString("nome"));
                    temp.get(i).getDadosPessoais().setDataNascimento(rs.getString("dataNascimento"));
                    temp.get(i).getDadosPessoais().setSexo(rs.getString("sexo"));
                    temp.get(i).getDadosPessoais().setRg(rs.getString("rg"));
                    temp.get(i).getDadosPessoais().setCpf(rs.getString("cpf"));
                    temp.get(i).getDadosPessoais().setEmail(rs.getString("email"));
                }
                rs.close();         // fechando rs
            } catch (SQLException ex){
                 
            }
             
            // Endereco
            String sqlE = "SELECT * FROM "+this.ENDERECO_PROFESSOR+" WHERE id_professor=?";
            try(PreparedStatement pstmt = connection.prepareStatement(sqlE)){
 
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
 
                if (rs.next()) {
                    temp.get(i).getDadosPessoais().getEndereco().setId(rs.getInt("idEndereco"));
                    temp.get(i).getDadosPessoais().getEndereco().setEstado(rs.getString("estado"));
                    temp.get(i).getDadosPessoais().getEndereco().setCidade(rs.getString("cidade"));
                    temp.get(i).getDadosPessoais().getEndereco().setLogradouro(rs.getString("logradouro"));
                    temp.get(i).getDadosPessoais().getEndereco().setNumero(rs.getInt("numero"));
                    temp.get(i).getDadosPessoais().getEndereco().setBairro(rs.getString("bairro"));
                    temp.get(i).getDadosPessoais().getEndereco().setComplemento(rs.getString("complemento"));
                }
                rs.close();             // fechando rs
            } catch (SQLException e){
             
            }
            // Telefone
            String sqlT = "SELECT * FROM "+this.TELEFONE_PROFESSOR+" WHERE id_professor=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlT)){
 
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
 
                if(rs.next()){
                    temp.get(i).getDadosPessoais().getTelefone().setId(rs.getInt("idTelefone"));
                    temp.get(i).getDadosPessoais().getTelefone().setOperadora(rs.getString("operadora"));
                    temp.get(i).getDadosPessoais().getTelefone().setPrefixo(rs.getString("prefixo"));
                    temp.get(i).getDadosPessoais().getTelefone().setNumero(rs.getString("numero"));
                }
                rs.close();
            } catch (SQLException e){
             
            }
            i++;
        }
        return temp;
    }
     
    public boolean existeProfessor(int id) throws ErroConexaoException{
         
        StringBuilder sqlQuery = new StringBuilder().append("SELECT matricula FROM ")
                .append(this.PROFESSOR)
                .append(" WHERE id_professor=?"); 
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())){
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
 
            if(rs.next()){
                return true;
            }
            rs.close();
        } catch (SQLException e){
            throw new ErroConexaoException("Erro metodo existeProfessor(int id)");
        }
        return false;
    }
 
    
    
    /**
	 * Retorna um Professor Objeto pelo nomeCompleto
	 * 
	 * @throws ProfessorInexistenteException 
	 * 
	 * */
	public Object pesquisaNome(String nomeCompleto) throws ErroConexaoException, NaoExisteException {
		Professor p = new Professor();
		int id = 0;
		
		// pesquisando id pelo nome completo
		StringBuilder sqlQueryID = new StringBuilder().append("SELECT id_professor FROM ")
				.append(this.DADOS_PESSOAIS_PROFESSOR).append(" WHERE nome=?");
		
		try (PreparedStatement pstmt = connection.prepareStatement(sqlQueryID.toString())) {
			pstmt.setString(1, nomeCompleto);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				 id = rs.getInt("id_professor");
			}
			rs.close();
		} catch (SQLException ex) {
			throw new ErroConexaoException("Erro connect pesquisaNome(nomeCompleto)");
		}
		if(id==0) {
			throw new NaoExisteException("Aluno nao cadastrado no sistema");
		}
		
		// Pesquisando todos os dados jah que agora tem o id do aluno
		StringBuilder sqlQueryDados = new StringBuilder().append("SELECT * FROM ")
				.append(this.PROFESSOR).append(" INNER JOIN ")
				.append(this.DADOS_PESSOAIS_PROFESSOR).append(" INNER JOIN ")
				.append(this.TELEFONE_PROFESSOR).append(" INNER JOIN ")
				.append(this.ENDERECO_PROFESSOR).append(" WHERE ").append(this.DADOS_PESSOAIS_PROFESSOR).append(".id_professor=?")
				;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sqlQueryDados.toString())) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			// Retornando o primeiro valor
			if (rs.next()) {
				
				// aluno
				p.setId(id);
				p.setMatricula(rs.getString("matricula"));
				p.setLattes(rs.getString("lattes"));
				
				// Endereco
				Endereco endereco = new Endereco();
				endereco.setId(rs.getInt("idEndereco"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setComplemento(rs.getString("complemento"));
				
				// Telefone
				Telefone tel = new Telefone();				
				tel.setId(rs.getInt("idTelefone"));
				tel.setOperadora(rs.getString("operadora"));
				tel.setPrefixo(rs.getString("prefixo"));
				tel.setNumero(rs.getString("numero"));
				
				// DadosPessoais
				DadosPessoais dados = new DadosPessoais();
				dados.setNome(rs.getString("nome"));
				dados.setDataNascimento(rs.getString("dataNascimento"));
				dados.setSexo(rs.getString("sexo"));
				dados.setRg(rs.getString("rg"));
				dados.setCpf(rs.getString("cpf"));
				dados.setEmail(rs.getString("email"));
				dados.setId(rs.getInt("IdDadosPessoais"));
				
				// setando informacoes
				dados.setEndereco(endereco);
				dados.setTelefone(tel);
				
				p.setDadosPessoais(dados);
				
			}
			rs.close();
		} catch (SQLException ex) {
			throw new ErroConexaoException("Erro connect pesquisaNome(nomeCompleto)");
		}		
		return p;
	}
	
	public int pesquisaIDNome(String nomeCompleto) throws ErroConexaoException{
		// pesquisando id pelo nome completo
		StringBuilder sqlQueryID = new StringBuilder().append("SELECT id_professor FROM ")
				.append(this.DADOS_PESSOAIS_PROFESSOR).append(" WHERE nome=?");
		
		try (PreparedStatement pstmt = connection.prepareStatement(sqlQueryID.toString())) {
			pstmt.setString(1, nomeCompleto);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				
				if(rs.getString("nome").equals(nomeCompleto)) {
					rs.close();
					return rs.getInt("id_professor");
				}

			}
			rs.close();
		} catch (SQLException ex) {
			throw new ErroConexaoException("Erro connect pesquisaIDNome(nomeCompleto)");
		}
		
		return 0;
	}
     
     
}