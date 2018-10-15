package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.controllers.ExceptionController;
import fi.basse.tikape.kysymyspankki.controllers.QuestionController;
import static fi.basse.tikape.kysymyspankki.ui.Renderer.render;
import java.util.HashMap;
import spark.Spark;
import static spark.Spark.get;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.post;

public class Main {
    
  public static void main(String[] args) throws Exception {

    // Public resources (css)
    Spark.staticFileLocation("/public");
        
    // Routes
    get("/", QuestionController::listQuestions);
    get("/add", QuestionController::formNew);
    post("/add", QuestionController::saveQuestion);
    
    // Error handling
    notFound(ExceptionController::notFound);
    internalServerError(ExceptionController::internalServerError);
  }
}