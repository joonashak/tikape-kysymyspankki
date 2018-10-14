package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.database.Database;
import fi.basse.tikape.kysymyspankki.database.QuestionDao;
import fi.basse.tikape.kysymyspankki.ui.ModelAndView;
import java.util.HashMap;
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
    
    Spark.get("/", (req, res) -> {
      HashMap model = new HashMap();
      return ModelAndView.createView("questions", model);
    }, new ThymeleafTemplateEngine());
  }
}
