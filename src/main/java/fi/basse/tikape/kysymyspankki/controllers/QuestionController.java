package fi.basse.tikape.kysymyspankki.controllers;

import fi.basse.tikape.kysymyspankki.database.Database;
import fi.basse.tikape.kysymyspankki.database.QuestionDao;
import fi.basse.tikape.kysymyspankki.domain.AnswerOption;
import fi.basse.tikape.kysymyspankki.domain.Question;
import static fi.basse.tikape.kysymyspankki.ui.Renderer.render;
import static fi.basse.tikape.kysymyspankki.utils.QueryParams.paramMap;
import static fi.basse.tikape.kysymyspankki.utils.Validator.validate;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;

public class QuestionController {
    
    // List all questions
    public static String listQuestions(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      HashMap model = new HashMap();
      model.put("courses", questionDao.getCourses());
      model.put("questions", questionDao.findAll());

      return render("questions", model);
    }
    
    // Show form for adding a new question
    public static String formNew(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      HashMap model = new HashMap();
      model.put("courses", questionDao.getCourses());
      
      return render("question", model);
    }
    
    // Create a new question
    public static String saveQuestion(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      // Gather POST data
      String[] keys = {"title", "body", "answer", "course", "newCourse"};
      Map<String, String> data = paramMap(req, keys);
      
      if (data.get("course") == null) {
        data.put("course", data.get("newCourse"));
      }
            
      
      // Validate data and display errors, if present
      Map<String, Boolean> validation = validate(data);
      validation.remove("newCourse");

      if (validation.containsValue(false)) {
        HashMap model = new HashMap();
        model.put("data", data);
        model.put("validation", validation);
        model.put("courses", questionDao.getCourses());

        HashMap message = new HashMap();
        message.put("error", "Check that you have filled all necessary fields.");
        model.put("message", message);
        
        return render("question", model);
      }
      
      Question question = new Question(
        0,
        data.get("course"),
        data.get("title"),
        data.get("body")
      );
      
      question.addAnswerOption(new AnswerOption(0, data.get("answer"), true));
      questionDao.save(question);
      
      return null;
    }
    
    // Form to edit question and answer options
    public static String formEdit(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      HashMap model = new HashMap();
      int id = Integer.parseInt(req.params("id"));
      model.put("data", questionDao.findOne(id));
      model.put("courses", questionDao.getCourses());
      model.put("edit", true);
      
      return render("question", model);
    }
}
