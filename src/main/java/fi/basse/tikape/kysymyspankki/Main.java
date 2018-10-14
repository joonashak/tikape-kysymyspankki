package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.ui.ModelAndView;
import java.util.HashMap;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    
  public static void main(String[] args) throws Exception {

    // Public resources (css)
    Spark.staticFileLocation("/public");

    Spark.get("/", (req, res) -> {
      HashMap model = new HashMap();
      return ModelAndView.createView("questions", model);
    }, new ThymeleafTemplateEngine());
  }
}
