package fi.basse.tikape.kysymyspankki;

import fi.basse.tikape.kysymyspankki.controllers.AnswerOptionController;
import fi.basse.tikape.kysymyspankki.controllers.ExceptionController;
import fi.basse.tikape.kysymyspankki.controllers.QuestionController;
import static spark.Spark.get;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Main {
    
  public static void main(String[] args) throws Exception {
    
    if (System.getenv("PORT") != null) {
            int port = Integer.parseInt(System.getenv("PORT"));
            port(port);
        }

    // Public resources (css)
    staticFileLocation("/public");
        
    // Routes
    get("/", QuestionController::listQuestions);
    
    get("/question/add", QuestionController::formNew);
    post("/question/add", QuestionController::saveQuestion);
    
    get("/question/edit/:id", QuestionController::formEdit);
    post("/question/edit", QuestionController::updateQuestion);
    
    get("/question/remove/:id", QuestionController::confirmRemove);
    post("/question/remove/:id", QuestionController::removeQuestion);
    
    post("/answeroption/add", AnswerOptionController::saveAnswerOption);
    
    get("/answeroption/remove/:id", AnswerOptionController::confirmRemove);
    post("/answeroption/remove/:id", AnswerOptionController::removeAnswerOption);
    
    // Error handling
    notFound(ExceptionController::notFound);
    internalServerError(ExceptionController::internalServerError);
  }
}