package edu.tamu.scholars.middleware.discovery.model.generated.organization;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class ConvenerOfEvent extends AbstractNestedDocument {
  private static final long serialVersionUID = 68641628L;

  private String date;

  public ConvenerOfEvent() {
    super();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
