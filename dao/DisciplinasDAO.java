package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
 
import exception.*;
import Sistema.*;
 
public class DisciplinasDAO {// implements OperacoesDisciplinasDAO{
    private final String DISCIPLINAS = "Disciplinas";
    private final String TURMA = "Turma";
    private final String LECIONA = "Leciona";
    private final String HISTORICO = "Historico";
    private Connection connection;
    private ProfessorDAO pdao;
    private AlunoDAO adao;
    private TurmaDAO tdao;
 
    public DisciplinasDAO() {
        try {
            connection = new ConnectionFactory().getConnection();
            adao = new AlunoDAO();
            pdao = new ProfessorDAO();
            tdao = new TurmaDAO();
 
        } catch (SQLException e) {
 
        }
    }
 
    // TODO: CONTINUAR AQUI a formatar
     
    /**
     * Informe o a disciplina e o aluno para serem colocar dos no sistema
     * */
    public void adicionaAluno(Disciplina disciplina, String cpfAluno)
            throws ErroConexaoException, NaoExisteException{
 
        // Verificar se o aluno esta no sistema v
        // Verificar a disciplina(turma) se esta no sistema v
         
        int id_aluno = 0;
        id_aluno = adao.pesquisaId(cpfAluno);
        if(id_aluno == 0) {
            throw new NaoExisteException("Aluno nao encontrado no sistema.");
        }
         
        int id_disciplina = 0;
        id_disciplina = existeTurma1(disciplina.getNome(), disciplina.getTurma());
        if(id_disciplina == 0 ) {
            throw new NaoExisteException("Disciplina nao cadastrada no sistema.");
        }
         
        StringBuilder sqlInsert = new StringBuilder().append("INSERT INTO ")
                .append(this.TURMA).append(" (id_aluno,id_disciplina)")
                .append(" VALUES(?,?)");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlInsert.toString())) {
            pstmt.setInt(1, id_aluno);          // id_aluno
            pstmt.setInt(2, id_disciplina);     // id_disciplina
             
            pstmt.execute();                    // executando acao
             
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro connection metodo adicionaAluno");
        }
         
    }
 
    /**
     * Remove apenas um aluno informando da disciplina o nome, turno, turma e a
     * matricula do aluno
     * 
     */
    public void removeAluno(String nomeDisciplina, String turno, String turma, String matriculaDoAluno)
            throws ErroConexaoException {
        String sql = "DELETE FROM " + this.DISCIPLINAS + " WHERE nome=? AND turno=? AND turma=? AND matricula_aluno=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeDisciplina); // nome da disciplina
            pstmt.setString(2, turno); // turno
            pstmt.setString(3, turma); // turma
            pstmt.setString(4, matriculaDoAluno); // matricula do aluno informado
 
            pstmt.execute();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro ao remover aluno da disciplina: " + nomeDisciplina);
        }
    }
 
    public void adicionaAlunos(Disciplina disciplina) throws ErroConexaoException {
        String sql = "INSERT INTO " + this.DISCIPLINAS
                + "(nome,turno,turma,matricula_aluno,matricula_professor) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
 
            for (int i = 0; i < disciplina.getAlunos().size(); i++) {
 
                pstmt.setString(1, disciplina.getNome()); // nome
                pstmt.setString(2, disciplina.getTurno()); // turno
                pstmt.setString(3, disciplina.getTurma()); // turma
                pstmt.setString(4, disciplina.getAlunos().get(i).getMatricula()); // matricula_aluno
                pstmt.setString(5, ""); // matricula_professor ""
 
                pstmt.execute(); // executar
            }
        } catch (SQLException e) {
            throw new ErroConexaoException("Erro ao adicionar alunos a disciplina " + disciplina.getNome() + "");
        }
    }
 
    public void removeAlunos(String nome, String turno, String turma) throws ErroConexaoException {
        String sql = "DELETE FROM " + this.DISCIPLINAS + " WHERE nome=? AND turno=? AND turma=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, turno);
            pstmt.setString(3, turma);
 
            pstmt.execute();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro ao remover alunos da disciplina " + nome);
        }
    }
 
    /**
     * Informe o nome da disciplina e a matricula do professor
     *
     *
     *
     */
    public void adicionaProfessor(String nomeDisciplina, String turno, String turma, String matriculaProfessor)
            throws ErroConexaoException {
        String sql = "UPDATE " + this.DISCIPLINAS + " SET matricula_professor=? WHERE nome=? AND turno=? AND turma=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(4, matriculaProfessor);
            pstmt.setString(1, nomeDisciplina);
            pstmt.setString(2, turno);
            pstmt.setString(3, turma);
 
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro ao vincular professor com a disciplina <" + nomeDisciplina + ">");
        }
    }
 
    /**
     * Informe a matricula do professor para remover ele da disciplina
     *
     *
     */
    public void removeProfessor(String nomeDisciplina, String turno, String turma, String matriculaProfessor)
            throws ErroConexaoException {
        String sql = "DELETE FROM " + this.DISCIPLINAS
                + " WHERE nome=? AND turno=? AND turma=? AND matricula_professor=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeDisciplina); // nome disciplina
            pstmt.setString(3, turno); // turno
            pstmt.setString(2, turma); // turma
            pstmt.setString(4, matriculaProfessor); // matricula professor
 
            pstmt.execute();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro ao remover professor da disciplina " + nomeDisciplina + "");
        }
    }
 
    // @Override
    public void remove() throws ErroConexaoException {
        // TODO Auto-generated method stub
 
    }
 
    // TODO: MODIFICANDO AQUI
    /**
     * nome, matricula, nomeAluno, turno, turma
     * 
     */
    public String[][] pesquisaAlunos(String nomeDisciplina) throws ErroConexaoException {
        int i = 0;
        String[][] temp = new String[100][2];
        String sql = "SELECT nome,matricula_aluno FROM " + this.DISCIPLINAS + " WHERE nome=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeDisciplina);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {
                temp[i][0] = rs.getString("nome");
                temp[i][1] = rs.getString("matricula_aluno");
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro ao pesquisar alunos da disciplina " + nomeDisciplina);
        }
        String[][] tempOk = new String[i][2];
        for (int j = 0; j < temp.length; j++) {
            tempOk[j][0] = temp[j][0];
            tempOk[j][1] = temp[j][1];
        }
 
        return tempOk;
    }
 
    public String[][] pesquisaProfessores(String nomeDisciplina) throws ErroConexaoException {
        int i = 0;
        String[][] temp = new String[100][2];
        String sql = "SELECT nome,matricula_professor FROM " + this.DISCIPLINAS + " WHERE nome=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeDisciplina);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {
                temp[i][0] = rs.getString("nome");
                temp[i][1] = rs.getString("matricula_professor");
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro ao pesquisar professores da disciplina: " + nomeDisciplina);
        }
        String[][] tempOk = new String[i][2];
        for (int j = 0; j < temp.length; j++) {
            tempOk[j][0] = temp[j][0];
            tempOk[j][1] = temp[j][1];
        }
 
        return tempOk;
    }
 
    /** Retorna null se nao houver */
    public String[][] pesquisaDisciplina(String nomeDisciplina, String turno, String turma)
            throws ErroConexaoException {
        String sqlP = "SELECT * FROM " + this.DISCIPLINAS + " WHERE nome=? AND turno=? AND turma=?";
        System.out.println(sqlP);
 
        String[][] temp = new String[1][8];
        try (PreparedStatement pstmt = connection.prepareStatement(sqlP)) {
            pstmt.setString(1, nomeDisciplina);
            pstmt.setString(2, turno);
            pstmt.setString(3, turma);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(">");
                temp[0][0] = "" + rs.getInt(1); // id
                temp[0][1] = rs.getString("nome"); // nome
                temp[0][2] = rs.getString("turno"); // turno
                temp[0][3] = rs.getString("turma"); // turma
                temp[0][4] = rs.getString("matricula_aluno"); // matricula_aluno
                temp[0][5] = rs.getString("matricula_professor"); // matricula_professor
                temp[0][6] = rs.getString("cpf_aluno"); // cpf_aluno
                temp[0][7] = rs.getString("cpf_professor"); // cpf_professor
                System.out.println(temp[0][7] + ">");
 
                return temp;
            }
 
        } catch (SQLException e) {
            throw new ErroConexaoException("Erro ao pesquisar disciplina");
        }
        return null;
    }
 
    // TODO: Mandar pra Juao
    public String[][] pesquisaAlunosDaDisciplina(String nomeDisciplina)
            throws ErroConexaoException, NaoExisteException {
        String[][] temp = new String[200][3]; // linha, coluna
        int i = 0;
        String sql = "select * from " + this.DISCIPLINAS + " where nome=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeDisciplina);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {
                temp[i][0] = rs.getString("nome_aluno");
                temp[i][1] = rs.getString("telefone_aluno");
                temp[i][2] = rs.getString("email_aluno");
 
                i++;
            }
            rs.close();
        } catch (SQLException e) {
            throw new ErroConexaoException("Erro ao pesquisar alunos da disciplina: " + nomeDisciplina);
        }
 
        if (i == 0) {
            throw new NaoExisteException("Disciplina nao encontrada ou nao existe alunos na disciplina");
        }
 
        String[][] alunos = new String[i][3];
        for (int j = 0; j < i; j++) {
            alunos[j][0] = temp[j][0]; // nome aluno
            alunos[j][1] = temp[j][1]; // telefone
            alunos[j][2] = temp[j][2]; // email
 
            // if(temp[j][1]==null) alunos[j][1] = ""; //telefone
            // else alunos[j][1] = temp[j][1];
            // if(temp[j][2]==null) alunos[j][2] = ""; //email
            // else alunos[j][2] = temp[j][2];
            //
        }
        return alunos;
    }
 
    // TODO: Mandar pra Juao
    public String[][] pesquisaProfessoresDaDisciplina(String nomeDisciplina)
            throws ErroConexaoException, NaoExisteException {
        String[][] temp = new String[200][3]; // linha, coluna
        int i = 0;
        String sql = "select * from " + this.DISCIPLINAS + " where nome=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeDisciplina);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {
                temp[i][0] = rs.getString("nome_professor");
                temp[i][1] = rs.getString("telefone_professor");
                temp[i][2] = rs.getString("email_professor");
 
                i++;
            }
            rs.close();
        } catch (SQLException e) {
            throw new ErroConexaoException("Erro ao pesquisar professores da disciplina: " + nomeDisciplina);
        }
        if (i == 0) {
            throw new NaoExisteException(
                    "Disciplina nao encontrada ou nao existe professores para a disciplina");
        }
        String[][] professores = new String[i][3];
        for (int j = 0; j < i; j++) {
            professores[j][0] = temp[j][0]; // nome professor
            professores[j][1] = temp[j][1]; // telefone
            professores[j][2] = temp[j][2]; // email
 
            // if(temp[j][1]==null) alunos[j][1] = ""; //telefone
            // else alunos[j][1] = temp[j][1];
            // if(temp[j][2]==null) alunos[j][2] = ""; //email
            // else alunos[j][2] = temp[j][2];
            //
        }
        return professores;
    }
 
    /**
     * Verifica se existe a disciplina com pelo nome, turno, turma
     *
     */
    public boolean existe() throws ErroConexaoException {
 
        // TODO Auto-generated method stub
        return false;
    }
 
    /**
     * Verifica se existe a aluno pela matricula
     *
     */
    public boolean existeAluno(String matricula) throws ErroConexaoException {
 
        return false;
    }
//TODO: MUDAR
//  public int pesquisaNome() {
//      String sqlPesquisa = "SELECT * FROM" + this.DISCIPLINASCAD;
//
//      return 0;
//  }
 
    /**
     * Retorna o id da disciplina
     * 
     * @return int 0 se nao achar, o id se achar
     */
    public int pesquisaID(String nome, String turma) throws ErroConexaoException {
        String sqlPesquisa = "SELECT idDisciplina FROM " + this.DISCIPLINAS + " WHERE nome=? and turma=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlPesquisa)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, turma);
 
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idDisciplina");
            }
 
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro ao pesquisar disciplina");
        }
        return 0;
 
    }
     
    // TODO: MODIFICAR
    /**
     * Modificar
     * */
    public boolean disciplinaNome(String nome) {
 
        return false;
    }
 
    // TODO: Novos
    /**
     * Retorna o nome, telefone, email
     * @throws TurmaVaziaException 
     * 
     */
    public String[][] AlunosDaDisciplina(String nomeDisciplina, String turma) throws ErroConexaoException, TurmaVaziaException {
         
        int id = 0;
        id = pesquisaID(nomeDisciplina, turma);                     // pegando id da disciplina
        if(id>0) {
            List<Integer> idsAluno = tdao.alunosDaDisciplina(id); // pegando id de todos os alunos dessa disciplina
            String[][] temp = new String[idsAluno.size()][3];
             
            for(int i=0;i<idsAluno.size();i++) {
                String[][] s = adao.nomeTelefoneEmail(idsAluno.get(i).intValue());
                temp[i][0] = s[0][0];
                temp[i][1] = s[0][1];
                temp[i][2] = s[0][2];
            }
            return temp;
        }
        throw new TurmaVaziaException("Nao tem alunos nessa turma, ou o nome informado");
    }
     
     
    /**
     * True or false
     * */
    public boolean existeTurma(String nome, String turma) throws ErroConexaoException{
        StringBuilder sqlQuery = new StringBuilder().append(" SELECT idDisciplina FROM ")
                .append(this.DISCIPLINAS)
                .append(" WHERE nome=? AND turma=?");
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setString(1, nome);
            pstmt.setString(2, turma);
             
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection existeTurma");
        }
        return false;
    }
     
    /**
     * int id
     * */
    public int existeTurma1(String nome, String turma) throws ErroConexaoException{
        StringBuilder sqlQuery = new StringBuilder().append(" SELECT idDisciplina FROM ")
                .append(this.DISCIPLINAS)
                .append(" WHERE nome=? AND turma=?");
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setString(1, nome);
            pstmt.setString(2, turma);
             
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("idDisciplina");
            }
            rs.close();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection existeTurma1");
        }
        return 0;
    }
     
    /**
     * Define o professor que irah lecionar a turma
     * @throws ErroConexaoException 
     * */
    public void lecionaProfessor(int idProfessor, int idDisciplina) throws ErroConexaoException {
        StringBuilder sqlInsert = new StringBuilder().append("INSERT INTO ").append(this.LECIONA).append("(id_professor)")
                .append(" VALUE(?)");
        try (PreparedStatement pstmt = connection.prepareStatement(sqlInsert.toString())) {
            pstmt.setInt(1, idProfessor);
            pstmt.setInt(2, idDisciplina);
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection lecionaProfessor");
        }
    }
     
    /**retorna true se achar ou false senao achar*/
    public boolean existeProfessor(int idDisciplina) throws ErroConexaoException {
        StringBuilder sqlQuery = new StringBuilder().append("SELECT id_professor FROM ")
                .append(this.LECIONA)
                .append(" WHERE id_disciplina=?");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setInt(1, idDisciplina);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) return true;
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection metodo existeProfessor");
        }
        return false;
    }
     
    /**Retorna o id do professor ou 0 senao achar*/
    public int existeProfessor1(int idDisciplina) throws ErroConexaoException {
        StringBuilder sqlQuery = new StringBuilder().append("SELECT id_professor FROM ")
                .append(this.LECIONA)
                .append(" WHERE id_disciplina=?");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setInt(1, idDisciplina);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) return rs.getInt("id_professor");
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection metodo existeProfessor1");
        }
        return 0;
    }
     
    /**A quantidade de alunos que existe na disciplina*/
    public int qdtAlunosTurma(int idDisciplina) throws ErroConexaoException  {
        int counter = 0;
        StringBuilder sqlQuery = new StringBuilder().append("SELECT id_aluno FROM ")
                .append(this.TURMA)
                .append(" WHERE id_disciplina=?");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setInt(1, idDisciplina);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                counter+=1;
            }
            rs.close();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection metodo existeProfessor1");
        }
        return counter;
    }
     
     
    // Se eu tenho o id � porque a disciplina existe, nao preciso verificar isso
    /**Remove professor da disciplina
     * @throws ErroConexaoException 
     * @throws ProfessorInexistenteException */
    public void removeLecionador(int idProfessor, int idDisciplina) throws ErroConexaoException {
         
        StringBuilder sqlDelete = new StringBuilder().append("DELETE FROM ")
                .append(this.LECIONA)
                .append(" WHERE id_professor=?");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlDelete.toString())) {
            pstmt.setInt(1, idProfessor);
            pstmt.execute();
             
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection metodo existeProfessor1");
        }
    }
     
     
    public boolean existeDisciplina(int idDisciplina) throws ErroConexaoException{
         
        StringBuilder sqlQuery = new StringBuilder().append("SELECT nome FROM ")
                .append(this.DISCIPLINAS)
                .append(" WHERE idDisciplina=?");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setInt(1, idDisciplina);
            ResultSet rs = pstmt.executeQuery();
             
            if(rs.next()) {
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection metodo existeProfessor1");
        }
        return false;
    }
     
     
    /**Retorna o id da disciplina 
     * Retorna o id ou 0 senao achar */
    public int existeDisciplina(String nomeDisciplina, String turma)  throws ErroConexaoException{
        StringBuilder sqlQuery = new StringBuilder().append("SELECT idDisciplina FROM ")
                .append(this.DISCIPLINAS)
                .append(" WHERE nomeDisciplina=? AND turma=?");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setString(1, nomeDisciplina);
            pstmt.setString(2, turma);
            ResultSet rs = pstmt.executeQuery();
         
            if(rs.next()) {
                return rs.getInt("idDisciplina");
            }
            rs.close();
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection metodo existeProfessor1");
        }
        return 0;
    }
     
     
    /**Retorna uma matriz com o nome, turno e turma das disciplinas que o professor leciona
     * @throws ProfessorInexistenteException */
    public String[][] professorLeciona(String matricula) throws ErroConexaoException, NaoExisteException{
        // verificar se o professor esta no db
        // pegar todos os ids da table Leciona do professor...
        // ...juntar com a table Disciplinas para pegar o nome, turno e turma
        // 
         
        int id = pdao.existeProfessor(matricula);
        
        if(id==0) {
            throw new NaoExisteException(new StringBuilder().append("Matricula invalida. ->")
                    .append(matricula).toString());
        }
         
        String[][] temp = new String[10][3];
        StringBuilder sqlQuery = new StringBuilder().append("SELECT id_disciplina FROM ")
                .append(this.LECIONA)
                .append(" WHERE id_professor=?");
         
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            int i=0;
            while (rs.next()) {
                 
                int idDisciplina = rs.getInt("id_disciplina");      // assim fica mais legivel
                 
                StringBuilder sqlQueryDisciplinas = new StringBuilder()
                        .append("SELECT nome,turno,turma FROM ")
                        .append(this.DISCIPLINAS)
                        .append(" WHERE idDisciplina=?");
                
                PreparedStatement ps = connection.prepareStatement(sqlQueryDisciplinas.toString());
                ps.setInt(1, idDisciplina);
                ResultSet r = ps.executeQuery();
                if(r.next()) {
                    temp[i][0] = r.getString("nome");
                    temp[i][1] = r.getString("turno");
                    temp[i][2] = r.getString("turma");
                }
                r.close();
            i++;    
            }
            rs.close();
             
            // Agora retornado o necessario
            String[][] dados = new String[i][3];
            for(int j=0;j<i;j++) {
                dados[j][0] = temp[j][0];
                dados[j][1] = temp[j][1];
                dados[j][2] = temp[j][2];
            }
            return dados;
        } catch (SQLException ex) {
            throw new ErroConexaoException("Erro conection metodo professorLeciona()");
        }
    }
    
    /**
     * Uma matriz com as informacoes do aluno com a nota de tal disciplina com apenas 1 bimestre
     *    0      1      2      3      4         5            6      7    =  8   <br>
     * [nota1][nota2][nota3][nota4][final][nomeDisciplina][turno][turma]		<- completo <br>
     * [nota1][nota2]  [ ]      [ ]  []   [nomeDisciplina][turno][turma]		<- senao estiver completo <br>
     * [nota1] [ ]     [ ]      [ ]  []   [nomeDisciplina][turno][turma]		<- senao estiver completo<br>
     * .<br>
     * .<br>
     * ...<br>
     * 
     * */
	public String[][] historico(int idAluno, int bimestre) throws ErroConexaoException {
		StringBuilder sqlQuery = new StringBuilder().append("SELECT * FROM ").append(this.HISTORICO)
				.append(" WHERE id_aluno=? and bimestre=?");

		String[][] temp = new String[10][8];
		try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
			pstmt.setInt(1, idAluno);
			pstmt.setInt(2, bimestre);

			ResultSet rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {

				temp[i][5] = String.valueOf(rs.getInt("id_disciplina")); // id -> nomeDisciplina

				boolean v = false;
				int a = 0;
				while (a < i) { // a<i
					// se achou, nao-icrementa
					// pega a posicao do a e coloca o valor dados[a][<indiceNota>] =
					// temp[a][<indiceNota>]
					if (Integer.parseInt(temp[a][5]) == rs.getInt("id_disciplina")) {
						v = true;
						break;
					}
					a++;
				}

				int identNota = rs.getInt("identNota");
				if (v) {
					if (identNota == 1)
						temp[a][0] = String.valueOf(rs.getDouble("nota")); // 1 nota

					else if (identNota == 2)
						temp[a][1] = String.valueOf(rs.getDouble("nota")); // 2 nota

					else if (identNota == 3)
						temp[a][2] = String.valueOf(rs.getDouble("nota")); // 3 nota

					else if (identNota == 4)
						temp[a][3] = String.valueOf(rs.getDouble("nota")); // 4 nota

					else if (identNota == 0)
						temp[a][4] = String.valueOf(rs.getDouble("nota")); // final
				} else {
					if (identNota == 1)
						temp[i][0] = String.valueOf(rs.getDouble("nota")); // 1 nota

					else if (identNota == 2)
						temp[i][1] = String.valueOf(rs.getDouble("nota")); // 2 nota

					else if (identNota == 3)
						temp[i][2] = String.valueOf(rs.getDouble("nota")); // 3 nota

					else if (identNota == 4)
						temp[i][3] = String.valueOf(rs.getDouble("nota")); // 4 nota

					else if (identNota == 0)
						temp[i][4] = String.valueOf(rs.getDouble("nota")); // final

					// acrescentando no i
					i++;
				}

			} // fim while rs.next

			rs.close();
			String[][] dados = new String[i][8];
			for (int j = 0; j < i; j++) {
				if (temp[j][0] == null)
					dados[j][0] = " "; // nota1
				else
					dados[j][0] = temp[j][0];

				if (temp[j][1] == null)
					dados[j][1] = " "; // nota2
				else
					dados[j][1] = temp[j][1];

				if (temp[j][2] == null)
					dados[j][2] = " "; // nota3
				else
					dados[j][2] = temp[j][2];

				if (temp[j][3] == null)
					dados[j][3] = " "; // nota4
				else
					dados[j][3] = temp[j][3];

				if (temp[j][4] == null)
					dados[j][4] = " "; // final
				else
					dados[j][4] = temp[j][4];

				String[][] infoDisciplina = disciplinaID(Integer.parseInt(temp[j][5]));

				dados[j][5] = infoDisciplina[0][0];// nome
				dados[j][6] = infoDisciplina[0][1];// turno
				dados[j][7] = infoDisciplina[0][2];// turma
			}
			return dados;

		} catch (SQLException ex) {
			throw new ErroConexaoException("Erro conection metodo historico(int idAluno, int bimestre)");
		}
	}
    
    /**
     * Informe o id que ele retorna uma matriz com [nome][turno][turma]
     * */
    public String[][] disciplinaID(int idDisciplina) throws ErroConexaoException{
    	// nome, turma, turno
    	String[][] dados = new String[1][3];
    	StringBuilder sqlQuery = new StringBuilder().append("SELECT nome,turno,turma FROM ")
    			.append(this.DISCIPLINAS)
    			.append(" WHERE idDisciplina=?");
    	try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
    		pstmt.setInt(1, idDisciplina);
    		
    		ResultSet rs = pstmt.executeQuery();
    		if(rs.next()) {
    			dados[0][0] = rs.getString("nome");
    			dados[0][1] = rs.getString("turno");
    			dados[0][2] = rs.getString("turma");
    		}
    	} catch(SQLException e) {
    		throw new ErroConexaoException("Erro connection metodo disciplina()");
    	}
    	return dados;
    }
    
    
    /**
     * Um array com as informacoes do aluno com a nota de tal disciplina
     *    0      1      2      3      4         5            6      7      8   =   9   <br>
     * [nota1][nota2][nota3][nota4][final][nomeDisciplina][turno][turma][bimestre1]		<- completo <br>
     * [nota1][nota2]  [ ]      [ ]  []   [nomeDisciplina][turno][turma][bimestre2]		<- senao estiver completo <br>
     * [nota1] [ ]     [ ]      [ ]  []   [nomeDisciplina][turno][turma][bimestre3]		<- senao estiver completo<br>
     * .<br>
     * .<br>
     * ...<br>
     * 
     * */
    //public String[][] historicoUmaDisciplina(int idAluno, int idDisciplina) throws ErroConexaoException {
	public String[][] historico1(int idAluno, int idDisciplina, int bimestre) throws ErroConexaoException {
		StringBuilder sqlQuery = new StringBuilder().append("SELECT * FROM ").append(this.HISTORICO)
				.append(" WHERE id_aluno=? and bimestre=?");
		
		String[][] temp = new String[10][8];
		try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
			pstmt.setInt(1, idAluno);
			pstmt.setInt(2, bimestre);
			
			ResultSet rs = pstmt.executeQuery();
			int i=0;
			while (rs.next()) {
				
				temp[i][5] = String.valueOf(rs.getInt("id_disciplina")); // id -> nomeDisciplina
				
				boolean v = false;
				int a=0;
				while(a<i) { // a<i
					// se achou, nao-icrementa
					// pega a posicao do a e coloca o valor dados[a][<indiceNota>] = temp[a][<indiceNota>]
					if(Integer.parseInt(temp[a][5]) == rs.getInt("id_disciplina")) {
						v = true;
						break;
					}
					a++;
				}
				
				int identNota = rs.getInt("identNota");
				if(v) {
					if (identNota == 1)
						temp[a][0] = String.valueOf(rs.getDouble("nota")); // 1 nota

					else if (identNota == 2)
						temp[a][1] = String.valueOf(rs.getDouble("nota")); // 2 nota

					else if (identNota == 3)
						temp[a][2] = String.valueOf(rs.getDouble("nota")); // 3 nota
					
					else if (identNota == 4)
						temp[a][3] = String.valueOf(rs.getDouble("nota")); // 4 nota

					else if (identNota == 0)
						temp[a][4] = String.valueOf(rs.getDouble("nota")); // final
				}else {
					if(identNota == 1)
						temp[i][0] = String.valueOf(rs.getDouble("nota")); // 1 nota					
					
					else if (identNota == 2)
						temp[i][1] = String.valueOf(rs.getDouble("nota")); // 2 nota
					
					else if (identNota == 3) 
						temp[i][2] = String.valueOf(rs.getDouble("nota")); // 3 nota
					
					else if (identNota == 4) 
						temp[i][3] = String.valueOf(rs.getDouble("nota")); // 4 nota
					
					else if(identNota == 0)
						temp[i][4] = String.valueOf(rs.getDouble("nota")); // final
					
					// acrescentando no i
					i++;
				}

			} // fim while  rs.next
			
			rs.close();
			String[][] dados = new String[i][8];
			for(int j=0;j<i;j++) {
				if(temp[j][0] == null) dados[j][0] = " "; // nota1
				else dados[j][0] = temp[j][0];
				
				if(temp[j][1] == null) dados[j][1] = " "; // nota2
				else dados[j][1] = temp[j][1];
				
				if(temp[j][2] == null) dados[j][2] = " "; // nota3
				else dados[j][2] = temp[j][2];
				
				if(temp[j][3] == null) dados[j][3] = " "; // nota4
				else dados[j][3] = temp[j][3];
				
				if(temp[j][4] == null) dados[j][4] = " "; // final
				else dados[j][4] = temp[j][4];
				
				String[][] infoDisciplina = disciplinaID(Integer.parseInt(temp[j][5]));
				
				dados[j][5] = infoDisciplina[0][0];// nome
				dados[j][6] = infoDisciplina[0][1];// turno
				dados[j][7] = infoDisciplina[0][2];// turma
			}
			return dados;
			
		} catch (SQLException ex) {
			throw new ErroConexaoException("Erro conection metodo historico(int idAluno, int bimestre)");
		}
	}
    
    
    
    
    
    
    
    
    
    
    
    //===========    
//	/**
//    *    0     1       2       3           4             5     6     = 7   <br>
//    * [nota1][nota2][nota3][notaFinal][nomeDisciplina][turno][turma]   <- completo <br>
//    * [nota1][nota2]  [ ]      [ ]    [nomeDisciplina][turno][turma]	<- senao estiver completo <br>
//    * [nota1] [ ]     [ ]      [ ]    [nomeDisciplina][turno][turma]   <- senao estiver completo
//    * 
//    * */
//	public String[][] historico(int idAluno, int bimestre) throws ErroConexaoException {
//		StringBuilder sqlQuery = new StringBuilder().append("SELECT * FROM ").append(this.HISTORICO)
//				.append(" WHERE id_aluno=? and bimestre=?");
//		
//		String[][] temp = new String[10][7];
//		try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())) {
//			pstmt.setInt(1, idAluno);
//			pstmt.setInt(2, bimestre);
//			
//			ResultSet rs = pstmt.executeQuery();
//			int i=0;
//			while (rs.next()) {
//				
//				temp[i][4] = String.valueOf(rs.getInt("id_disciplina")); // id -> nomeDisciplina
//				
//				boolean v = false;
//				int a=0;
//				while(a<i) { // a<i
//					// se achou, nao-icrementa
//					// pega a posicao do a e coloca o valor dados[a][<indiceNota>] = temp[a][<indiceNota>]
//					if(Integer.parseInt(temp[a][4]) == rs.getInt("id_disciplina")) {
//						v = true;
//						break;
//					}
//					a++;
//				}
//				
//				int identNota = rs.getInt("identNota");
//				if(v) {
//					if (identNota == 1)
//						temp[a][0] = String.valueOf(rs.getDouble("nota")); // 1 nota
//
//					else if (identNota == 2)
//						temp[a][1] = String.valueOf(rs.getDouble("nota")); // 2 nota
//
//					else if (identNota == 3)
//						temp[a][2] = String.valueOf(rs.getDouble("nota")); // 3 nota
//
//					else if (identNota == 0)
//						temp[a][3] = String.valueOf(rs.getDouble("nota")); // final
//				}else {
//					if(identNota == 1) {
//						temp[i][0] = String.valueOf(rs.getDouble("nota")); // 1 nota					
//					}else if (identNota == 2) {
//						temp[i][1] = String.valueOf(rs.getDouble("nota")); // 2 nota
//					}else if (identNota == 3) {
//						temp[i][2] = String.valueOf(rs.getDouble("nota")); // 3 nota
//					}else if(identNota == 0){
//						temp[i][3] = String.valueOf(rs.getDouble("nota")); // final
//					}
//					// acrescentando no i
//					i++;
//				}
//
//			} // fim while  rs.next
//			
//			rs.close();
//			String[][] dados = new String[i][7];
//			for(int j=0;j<i;j++) {
//				if(temp[j][0] == null) dados[j][0] = " "; // nota1
//				else dados[j][0] = temp[j][0];
//				
//				if(temp[j][1] == null) dados[j][1] = " "; // nota2
//				else dados[j][1] = temp[j][1];
//				
//				if(temp[j][2] == null) dados[j][2] = " "; // nota3
//				else dados[j][2] = temp[j][2];
//				
//				if(temp[j][3] == null) dados[j][3] = " "; // final
//				else dados[j][3] = temp[j][3];
//				
//				String[][] infoDisciplina = disciplinaID(Integer.parseInt(temp[j][4]));
//				
//				dados[j][4] = infoDisciplina[0][0];// nome
//				dados[j][5] = infoDisciplina[0][1];// turno
//				dados[j][6] = infoDisciplina[0][2];// turma
//			}
//			return dados;
//			
//		} catch (SQLException ex) {
//			throw new ErroConexaoException("Erro conection metodo historico(int idAluno, int bimestre)");
//		}
//	}
   
    
}