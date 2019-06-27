package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class HasExpertiseInTechnique extends AbstractNestedDocument {
  private static final long serialVersionUID = -1505106631L;

  private String type;

  public HasExpertiseInTechnique() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
