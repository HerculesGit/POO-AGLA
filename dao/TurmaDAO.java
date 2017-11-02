package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import exception.ErroConexaoException;
import exception.TurmaVaziaException;
 
public class TurmaDAO {
     
    private Connection connection;
    private final String TURMA="Turma";
     
    public TurmaDAO()
    {
        try {
            this.connection = new ConnectionFactory().getConnection();
        } catch (SQLException e) {
 
        }
    }
     
    /**
     * Retorna List<Integer> com o id dos Alunos que estao na disciplina
     * @throws ErroConexaoException 
     * @throws TurmaVaziaException 
     * */
    public List<Integer> alunosDaDisciplina(int idDisciplina) throws ErroConexaoException, TurmaVaziaException{
         
		StringBuilder sqlQuery = new StringBuilder().append("SELECT * FROM ").append(this.TURMA)
				.append(" WHERE id_disciplina=?");
		
        List<Integer> temp = new ArrayList<Integer>();
        try(PreparedStatement pstmt = connection.prepareStatement(sqlQuery.toString())){
             
            pstmt.setInt(1, idDisciplina);
            ResultSet rs = pstmt.executeQuery();
             
            // enquanto estiver dados
            while (rs.next()) {
                temp.add(rs.getInt("id_aluno"));
            }
            rs.close();
        } catch (SQLException e){
            throw new ErroConexaoException("Erro ao conectar com a turma -Turma-");
        }
         
        if(temp.isEmpty()) throw new TurmaVaziaException("Nenhum aluno na turma");
         
        return temp;
    }
     
    /**Retorna um boolean afirmando se ele esta na turma ou nao*/
    public boolean alunoNaTurma(int idAluno, int idDisciplina)  throws ErroConexaoException{
        //StringBuilder sql = "select * from "+this.TURMA+" where  id_aluno=? and id_disciplina=?";
        String sql ="select * from "+this.TURMA+" where  id_aluno=? and id_disciplina=?" ;
         
        try(PreparedStatement pstmt = connection.prepareStatement(sql.toString())){
             
            pstmt.setInt(1, idAluno);
            pstmt.setInt(2, idDisciplina);
             
            ResultSet rs = pstmt.executeQuery();
 
            if (rs.next()) {
                return true;
            }
            rs.close();             // fechando rs
        } catch (SQLException e){
            throw new ErroConexaoException("Erro na ao conectar com a turma -Turma-");
        }
        return false;
    }
     
}