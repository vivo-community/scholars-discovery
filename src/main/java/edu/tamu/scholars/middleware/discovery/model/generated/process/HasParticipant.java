package edu.tamu.scholars.middleware.discovery.model.generated.process;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class HasParticipant extends AbstractNestedDocument {
  private static final long serialVersionUID = -1521886252209913426L;

  private String type;

  public HasParticipant() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
