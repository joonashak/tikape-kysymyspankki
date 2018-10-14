package fi.basse.tikape.kysymyspankki.domain;

public class AnswerOption {
  
  private String body;
  private boolean correct;

  public AnswerOption(String body, boolean correct) {
    this.body = body;
    this.correct = correct;
  }

  public String getBody() {
    return body;
  }

  public boolean isCorrect() {
    return correct;
  }
}
