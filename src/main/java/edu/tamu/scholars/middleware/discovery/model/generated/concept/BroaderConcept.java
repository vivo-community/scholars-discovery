package edu.tamu.scholars.middleware.discovery.model.generated.concept;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class BroaderConcept extends AbstractNestedDocument {
  private static final long serialVersionUID = 866710141L;

  private String type;

  public BroaderConcept() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
