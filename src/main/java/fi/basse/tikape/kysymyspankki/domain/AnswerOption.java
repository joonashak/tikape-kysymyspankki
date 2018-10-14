package fi.basse.tikape.kysymyspankki.domain;

public class AnswerOption {
  
  private Integer id;
  private String body;
  private boolean correct;

  public AnswerOption(Integer id, String body, boolean correct) {
    this.id = id;
    this.body = body;
    this.correct = correct;
  }

  public Integer getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public boolean isCorrect() {
    return correct;
  }
}
