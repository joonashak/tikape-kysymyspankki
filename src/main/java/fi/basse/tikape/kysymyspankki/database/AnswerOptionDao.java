package fi.basse.tikape.kysymyspankki.database;

import fi.basse.tikape.kysymyspankki.domain.AnswerOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
