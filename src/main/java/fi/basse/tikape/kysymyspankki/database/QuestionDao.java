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
  
  public Question findOne(int id) throws SQLException {
    Connection conn = super.getConnection();
    
    String sql = "SELECT Question.*, AnswerOption.id AS answeroption_id, Answeroption.body AS answeroption_body, AnswerOption.correct FROM Question LEFT JOIN AnswerOption ON AnswerOption.question_id = Question.id WHERE Question.id = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs = stmt.executeQuery();
    
    // Build result
    Question question = null;
    
    while (rs.next()) {
      // Creeate question if not exist
      if (question == null) {
        question = new Question(
          id,
          rs.getString("courseName"),
          rs.getString("title"), 
          rs.getString("body")
        );
      }
      
      // Add answer option
      question.addAnswerOption(new AnswerOption(
        rs.getInt("answeroption_id"),
        rs.getString("answeroption_body"),
        rs.getBoolean("correct")
      ));
    }
    
    super.close(rs, stmt);
    return question;
  }
  
  public List<Question> findAll() throws SQLException {
    Connection conn = super.getConnection();
    
    String sql = "SELECT Question.*, AnswerOption.id AS answeroption_id, AnswerOption.body AS answeroption_body, AnswerOption.correct FROM Question INNER JOIN AnswerOption ON AnswerOption.question_id = Question.id";
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
      
      // Create question if not exist
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
        rs.getString("answeroption_body"),
        rs.getBoolean("correct")
      ));
    }
    
    super.close(rs, stmt);
    return questions;
  }
  
  public List<String> getCourses() throws SQLException {
    Connection conn = super.getConnection();

    // Find unique course names
    String sql = "SELECT DISTINCT coursename FROM Question ORDER BY coursename DESC";
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    
    // Build results
    List<String> courses = new ArrayList<>();
    
    while (rs.next()) {
      courses.add(rs.getString("coursename"));
    }
    
    super.close(rs, stmt);
    return courses;
  }
  
  // The question to be saved must include the correct answer as the only answer
  public void save(Question question) throws SQLException {
    // TODO: data validation
    
    // Save question
    Connection conn = super.getConnection();
    String sql = "INSERT INTO Question (coursename, title, body) VALUES (?,?,?) RETURNING id";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, question.getCourse());
    stmt.setString(2, question.getTitle());
    stmt.setString(3, question.getBody());

    ResultSet rs = stmt.executeQuery();
    
    // Save answer
    rs.next();
    Integer id = rs.getInt("id");
    AnswerOption ao = (AnswerOption) question.getAnswerOptions().get(0);
    AnswerOptionDao aoDao = new AnswerOptionDao(super.getDb());
    aoDao.save(ao, id);
    
    super.close(rs, stmt);
  }
}
