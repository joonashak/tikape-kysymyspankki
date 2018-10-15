package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.controllers.QuestionController;
import spark.Spark;
import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    
  public static void main(String[] args) throws Exception {

    // Public resources (css)
    Spark.staticFileLocation("/public");
        
    // Routes
    get("/", QuestionController::listQuestions);
    get("/add", QuestionController::formNew);
    post("/add", QuestionController::saveQuestion);
    
  }
}