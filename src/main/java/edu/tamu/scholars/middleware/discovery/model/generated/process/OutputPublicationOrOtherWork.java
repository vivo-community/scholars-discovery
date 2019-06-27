package edu.tamu.scholars.middleware.discovery.model.generated.process;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class OutputPublicationOrOtherWork extends AbstractNestedDocument {
  private static final long serialVersionUID = 2563097765095941150L;

  private String type;

  public OutputPublicationOrOtherWork() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
