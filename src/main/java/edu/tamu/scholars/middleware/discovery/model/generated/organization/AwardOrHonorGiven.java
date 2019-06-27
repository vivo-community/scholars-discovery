package edu.tamu.scholars.middleware.discovery.model.generated.organization;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class AwardOrHonorGiven extends AbstractNestedDocument {
  private static final long serialVersionUID = -5125982323542746967L;

  private String date;

  public AwardOrHonorGiven() {
    super();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
