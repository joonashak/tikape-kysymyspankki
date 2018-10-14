package fi.basse.tikape.kysymyspankki.ui;

import java.util.Map;

public class ModelAndView {
    
  public static spark.ModelAndView createView(String viewName, Map model) {
    model.put("viewName", viewName);

    return new spark.ModelAndView(model, "fragments/template");
  }
}
