package fi.basse.tikape.kysymyspankki.database;

import fi.basse.tikape.kysymyspankki.domain.AnswerOption;
import fi.basse.tikape.kysymyspankki.domain.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends Dao {

  public QuestionDao(Database db) {
    super(db);
  }
  
  public List<Question> findAll() throws SQLException {
    Connection conn = super.getConnection();
    
    String sql = "SELECT Question.*, AnswerOption.id AS answeroption_id, AnswerOption.body, AnswerOption.correct FROM Question INNER JOIN AnswerOption ON AnswerOption.question_id = Question.id";
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    
    // Build results
    List<Question> questions = new ArrayList<>();
    
    while (rs.next()) {
      Integer id = rs.getInt("id");
      
      Question question = questions.stream()
              .filter(q -> q.getId() == id)
              .findFirst()
              .orElse(null);
      
      // Creeate question if not exist
      if (question == null) {
        question = new Question(
                id,
                rs.getString("courseName"),
                rs.getString("title"), 
                rs.getString("body")
          );
        questions.add(question);
      }
      
      // Add answer option
      question.addAnswerOption(new AnswerOption(
              rs.getInt("answeroption_id"),
              rs.getString("body"),
              rs.getBoolean("correct")
        ));
    }
    
    super.close(rs, stmt);
    return questions;
  }
}
