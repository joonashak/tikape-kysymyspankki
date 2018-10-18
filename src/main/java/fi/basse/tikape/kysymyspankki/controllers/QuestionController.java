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
    public static String listQuestions(Request req, Response res, Map model) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      model.put("courses", questionDao.getCourses());
      model.put("questions", questionDao.findAll());

      return render("questions", model);
    }

    // Interface for Spark
    public static String listQuestions(Request req, Response res) throws SQLException {
      HashMap model = new HashMap();
      return listQuestions(req, res, model);
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
      Question question = questionDao.findOne(Integer.parseInt(req.params("id")));
      model.put("data", question);
      model.put("answerOptions", question.getAnswerOptions());
      model.put("courses", questionDao.getCourses());
      model.put("edit", true);
      
      return render("question", model);
    }
    
    public static String updateQuestion(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      // Gather POST data
      String[] keys = {"id", "title", "body", "course", "newCourse"};
      Map<String, String> data = paramMap(req, keys);
      
      if (data.get("course") == null) {
        data.put("course", data.get("newCourse"));
      }
            
      
      // Validate data and display errors, if present
      Map<String, Boolean> validation = validate(data);
      validation.remove("newCourse");
      
      if (validation.containsValue(false)) {
        HashMap model = new HashMap();
        
        // This prevents new answer field being marked as erroneous
        validation.put("answer", true);
        
        // Populate answer options as they are not POSTed
        Question question = questionDao.findOne(Integer.parseInt(data.get("id")));
        model.put("answerOptions", question.getAnswerOptions());
        
        model.put("data", data);
        model.put("validation", validation);
        model.put("courses", questionDao.getCourses());
        model.put("edit", true);

        HashMap message = new HashMap();
        message.put("error", "Check that you have filled all necessary fields.");
        model.put("message", message);
        
        return render("question", model);
      }
      
      int id = Integer.parseInt(data.get("id"));
      Question question = new Question(
        id,
        data.get("course"),
        data.get("title"),
        data.get("body")
      );
      questionDao.update(question);
      
      HashMap model = new HashMap();
      HashMap message = new HashMap();
      message.put("success", "Question #" + id + " updated successfully.");
      model.put("message", message);
      
      return listQuestions(req, res, model);
    }
    
    // Show confirmation prompt for removing a question
    public static String confirmRemove(Request req, Response res) {
      HashMap model = new HashMap();
      model.put("id", req.params("id"));
      
      return render("remove", model);
    }
    
    // Remove a question
    public static String removeQuestion(Request req, Response res) throws SQLException {
      Database db = new Database();
      QuestionDao questionDao = new QuestionDao(db);
      
      int id = Integer.parseInt(req.params("id"));
      questionDao.delete(id);
      
      HashMap model = new HashMap();
      HashMap message = new HashMap();
      message.put("success", "Question #" + id + " was removed.");
      model.put("message", message);
      
      return listQuestions(req, res, model);
    }
}
