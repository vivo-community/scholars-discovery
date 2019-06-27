package edu.tamu.scholars.middleware.discovery.model.generated.organization;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class Presentation extends AbstractNestedDocument {
  private static final long serialVersionUID = 7410591919941216602L;

  private String event;

  private String date;

  public Presentation() {
    super();
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
