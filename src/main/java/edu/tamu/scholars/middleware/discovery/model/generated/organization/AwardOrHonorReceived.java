package edu.tamu.scholars.middleware.discovery.model.generated.organization;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class AwardOrHonorReceived extends AbstractNestedDocument {
  private static final long serialVersionUID = 8233969555511035225L;

  private String date;

  public AwardOrHonorReceived() {
    super();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
