package fi.basse.tikape.kysymyspankki.ui;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Renderer {
    
  public static String render(String viewName, Map model) {
    model.put("viewName", viewName);
    
    // Add empty maps for data and message, if none exists.
    // Avoids creating empty maps in controllers.
    if (!model.containsKey("data")) {
      model.put("data", new HashMap());
    }
    
    if (!model.containsKey("message")) {
      model.put("message", new HashMap());
    }
    
    ModelAndView mv = new spark.ModelAndView(model, "fragments/template");
    ThymeleafTemplateEngine tte = new ThymeleafTemplateEngine();
    
    return tte.render(mv);
  }
}
