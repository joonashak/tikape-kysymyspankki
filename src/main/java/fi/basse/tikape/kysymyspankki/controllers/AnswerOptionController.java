package fi.basse.tikape.kysymyspankki.controllers;

import fi.basse.tikape.kysymyspankki.database.AnswerOptionDao;
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

public class AnswerOptionController {
  
  // Create a new answer option
  public static String saveAnswerOption(Request req, Response res) throws SQLException {
    Database db = new Database();
    QuestionDao questionDao = new QuestionDao(db);
    AnswerOptionDao aoDao = new AnswerOptionDao(db);
    
    HashMap model = new HashMap();
    
    // Gather POST data
    String[] keys = {"answer", "correct", "questionId"};
    Map<String, String> data = paramMap(req, keys);
    Integer questionId = Integer.parseInt(data.get("questionId"));
    
    // Validate data
    Map<String, Boolean> validation = validate(data);
    
    // Prevent question fields from being marked as erroneous
    validation.put("title", true);
    validation.put("body", true);
    validation.put("course", true);

    if (validation.containsValue(false)) {
      // Show validation error
      HashMap message = new HashMap();
      message.put("error", "Check that you have filled all necessary fields.");
      model.put("message", message);
      model.put("answer", data);
    } else {
      // Or save the answer option
      AnswerOption ao = new AnswerOption(
        questionId,
        data.get("answer"),
        Boolean.parseBoolean(data.get("correct"))
      );
      aoDao.save(ao, questionId);
    }
    
    Question question = questionDao.findOne(questionId);
    model.put("answerOptions", question.getAnswerOptions());
    model.put("data", question);
    model.put("validation", validation);
    model.put("courses", questionDao.getCourses());
    model.put("edit", true);
    
    return render("question", model);
  }
}
