package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class HasExpertiseInTechnique extends AbstractNestedDocument {
  private static final long serialVersionUID = -3244714432010664587L;

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
