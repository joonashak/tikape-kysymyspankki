package fi.basse.tikape.kysymyspankki.ui;

import java.util.Map;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Renderer {
    
  public static String render(String viewName, Map model) {
    model.put("viewName", viewName);
    ModelAndView mv = new spark.ModelAndView(model, "fragments/template");
    ThymeleafTemplateEngine tte = new ThymeleafTemplateEngine();
    
    return tte.render(mv);
  }
}
