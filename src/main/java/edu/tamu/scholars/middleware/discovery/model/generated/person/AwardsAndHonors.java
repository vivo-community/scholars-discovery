package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class AwardsAndHonors extends AbstractNestedDocument {
  private static final long serialVersionUID = 902047119L;

  private String date;

  public AwardsAndHonors() {
    super();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
