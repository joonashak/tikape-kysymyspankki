package fi.basse.tikape.kysymyspankki.database;

import fi.basse.tikape.kysymyspankki.domain.AnswerOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AnswerOptionDao extends Dao {
  
  public AnswerOptionDao(Database db) {
    super(db);
  }
  
  public void save(AnswerOption ao, Integer questionId) throws SQLException {
    Connection conn = super.getConnection();
    
    String sql = "INSERT INTO AnswerOption (question_id, body, correct) VALUES (?,?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, questionId);
    stmt.setString(2, ao.getBody());
    stmt.setBoolean(3, ao.isCorrect());
    stmt.executeUpdate();
  }
  
  // Remove an answer option
  public void delete(int id) throws SQLException {
    Connection conn = super.getConnection();
    String sql = "DELETE FROM AnswerOption WHERE id = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    
    stmt.executeUpdate();
  }
  
  // Find corresponding question id
  public int findQuestionId(int id) throws SQLException {
    Connection conn = super.getConnection();
    String sql = "SELECT Question.id FROM Question LEFT JOIN AnswerOption ON AnswerOption.question_id = Question.id WHERE AnswerOption.id = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    
    ResultSet rs = stmt.executeQuery();
    
    if (!rs.next()) {
      throw new SQLException("Answer option not found");
    }
    
    return rs.getInt("id");
  }
  
  // Check if an answer option can be removed
  public boolean canRemove(int aoId) throws SQLException {
    Connection conn = super.getConnection();
    String sql = "SELECT AnswerOption.correct FROM Question LEFT JOIN AnswerOption ON AnswerOption.question_id = Question.id WHERE Question.id = ? AND AnswerOption.id != ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, findQuestionId(aoId));
    stmt.setInt(2, aoId);
    
    ResultSet rs = stmt.executeQuery();
    Set<Boolean> truths = new HashSet<>();
    
    while (rs.next()) {
      truths.add(rs.getBoolean("correct"));
    }
    
    return truths.contains(true);
  }
}
