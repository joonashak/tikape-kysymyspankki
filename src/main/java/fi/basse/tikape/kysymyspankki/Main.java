package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.database.Database;
import fi.basse.tikape.kysymyspankki.database.QuestionDao;
import fi.basse.tikape.kysymyspankki.domain.AnswerOption;
import fi.basse.tikape.kysymyspankki.domain.Question;
import fi.basse.tikape.kysymyspankki.ui.ModelAndView;
import java.util.HashMap;
import java.util.List;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    
  public static void main(String[] args) throws Exception {

    // Public resources (css)
    Spark.staticFileLocation("/public");
    
    // Database access
    Database db = new Database();
    db.getConnection();
    QuestionDao questionDao = new QuestionDao(db);
    
    // List all questions
    Spark.get("/", (req, res) -> {
      HashMap model = new HashMap();
      model.put("courses", questionDao.getCourses());
      model.put("questions", questionDao.findAll());
      
      return ModelAndView.createView("questions", model);
    }, new ThymeleafTemplateEngine());
    
    // Form to add a new question
    Spark.get("/add", (req, res) -> {
      HashMap model = new HashMap();
      model.put("courses", questionDao.getCourses());
      
      return ModelAndView.createView("add", model);
    }, new ThymeleafTemplateEngine());
    
    // Create new question in database
    // TODO: Error handling
    Spark.post("/add", (req, res) -> {
      String title = req.queryParams("title");
      String body = req.queryParams("question");
      String answer = req.queryParams("answer");
      
      String selectedCourse = req.queryParams("course");
      String course = selectedCourse != null ? selectedCourse : req.queryParams("newCourse");
      
      Question question = new Question(0, course, title, body);
      question.addAnswerOption(new AnswerOption(0, answer, true));
      questionDao.save(question);
      
      return null;
    });
  }
}
