package fi.basse.tikape.kysymyspankki.controllers;

import static fi.basse.tikape.kysymyspankki.ui.Renderer.render;
import java.util.HashMap;
import spark.Request;
import spark.Response;

public class ExceptionController {
  
  public static String notFound(Request req, Response res) {
    return errorPage("Error: 404 Not Found",
            "The resource you requested could not be found.");
  }
  
  public static String internalServerError(Request req, Response res) {
    return errorPage("Error: 500 Internal Server Error",
            "The server encountered an unexpected error and was unable to process your request.");
  }
  
  private static String errorPage(String title, String message) {
    HashMap model = new HashMap();
    model.put("title", title);
    model.put("message", message);
    
    return render("error", model);
  }
}
