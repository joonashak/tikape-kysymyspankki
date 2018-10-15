package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.controllers.QuestionController;
import spark.Spark;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    
  public static void main(String[] args) throws Exception {

    // Public resources (css)
    Spark.staticFileLocation("/public");
        
    // Routes
    get("/", QuestionController::listQuestions, new ThymeleafTemplateEngine());
    get("/add", QuestionController::formNew, new ThymeleafTemplateEngine());
    post("/add", QuestionController::saveQuestion, new ThymeleafTemplateEngine());
    
  }
}
