package fi.basse.tikape.kysymyspankki.controllers;

import fi.basse.tikape.kysymyspankki.database.Database;
import fi.basse.tikape.kysymyspankki.database.QuestionDao;
import fi.basse.tikape.kysymyspankki.domain.AnswerOption;
import fi.basse.tikape.kysymyspankki.domain.Question;
import static fi.basse.tikape.kysymyspankki.ui.ModelAndView.createView;
import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class QuestionController {
    
    // List all questions
    public static ModelAndView listQuestions(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      HashMap model = new HashMap();
      model.put("courses", questionDao.getCourses());
      model.put("questions", questionDao.findAll());

      
      return createView("questions", model);
    }
    
    // Show form for adding a new question
    public static ModelAndView formNew(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      HashMap model = new HashMap();
      model.put("courses", questionDao.getCourses());
      
      return createView("add", model);
    }
    
    // Create a new question
    public static ModelAndView saveQuestion(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      String title = req.queryParams("title");
      String body = req.queryParams("question");
      String answer = req.queryParams("answer");
      
      String selectedCourse = req.queryParams("course");
      String course = selectedCourse != null ? selectedCourse : req.queryParams("newCourse");
      
      Question question = new Question(0, course, title, body);
      question.addAnswerOption(new AnswerOption(0, answer, true));
      questionDao.save(question);
      
      return null;
    }
}
