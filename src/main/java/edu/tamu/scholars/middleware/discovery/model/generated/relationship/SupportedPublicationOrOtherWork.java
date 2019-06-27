package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class SupportedPublicationOrOtherWork extends AbstractNestedDocument {
  private static final long serialVersionUID = -1237108952L;

  private String type;

  public SupportedPublicationOrOtherWork() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
