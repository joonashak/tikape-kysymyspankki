package fi.basse.tikape.kysymyspankki.ui;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Renderer {
    
  public static String render(String viewName, Map model) {
    model.put("viewName", viewName);
    
    // Add empty maps, if necessary.
    // Avoids creating empty maps in controllers.
    String[] keys = {"data", "message", "validation"};
    
    for (String key : keys) {
      if (!model.containsKey(key)) {
        model.put(key, new HashMap());
      }
    }
    
    ModelAndView mv = new spark.ModelAndView(model, "fragments/template");
    ThymeleafTemplateEngine tte = new ThymeleafTemplateEngine();
    
    return tte.render(mv);
  }
}
