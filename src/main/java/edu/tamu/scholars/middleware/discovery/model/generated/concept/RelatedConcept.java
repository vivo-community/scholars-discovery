package edu.tamu.scholars.middleware.discovery.model.generated.concept;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class RelatedConcept extends AbstractNestedDocument {
  private static final long serialVersionUID = 1589142232695903862L;

  private String type;

  public RelatedConcept() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
