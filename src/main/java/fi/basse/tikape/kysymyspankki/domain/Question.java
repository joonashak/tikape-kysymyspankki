package fi.basse.tikape.kysymyspankki.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {
  
  private Integer id;
  private String courseName;
  private String title;
  private String body;
  private List answerOptions;

  public Question(Integer id, String courseName, String title, String body) {
    this.id = id;
    this.courseName = courseName;
    this.title = title;
    this.body = body;
    this.answerOptions = new ArrayList();
  }

  public Integer getId() {
    return id;
  }

  public String getCourseName() {
    return courseName;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public List getAnswerOptions() {
    return answerOptions;
  }
  
  public void addAnswerOption(AnswerOption ao) {
    answerOptions.add(ao);
  }
}
