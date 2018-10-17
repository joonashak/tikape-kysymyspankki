package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.controllers.ExceptionController;
import fi.basse.tikape.kysymyspankki.controllers.QuestionController;
import static spark.Spark.get;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Main {
    
  public static void main(String[] args) throws Exception {

    // Public resources (css)
    staticFileLocation("/public");
        
    // Routes
    get("/", QuestionController::listQuestions);
    get("/add", QuestionController::formNew);
    post("/add", QuestionController::saveQuestion);
    
    // Error handling
    notFound(ExceptionController::notFound);
    internalServerError(ExceptionController::internalServerError);
  }
}