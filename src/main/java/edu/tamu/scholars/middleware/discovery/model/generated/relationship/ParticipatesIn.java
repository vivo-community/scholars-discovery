package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class ParticipatesIn extends AbstractNestedDocument {
  private static final long serialVersionUID = 10591314L;

  private String type;

  public ParticipatesIn() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
