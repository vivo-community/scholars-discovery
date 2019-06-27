package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class Credential extends AbstractNestedDocument {
  private static final long serialVersionUID = 520049810L;

  private String dateIssued;

  public Credential() {
    super();
  }

  public String getDateIssued() {
    return dateIssued;
  }

  public void setDateIssued(String dateIssued) {
    this.dateIssued = dateIssued;
  }
}
