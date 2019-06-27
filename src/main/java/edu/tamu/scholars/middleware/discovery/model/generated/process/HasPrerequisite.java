package edu.tamu.scholars.middleware.discovery.model.generated.process;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class HasPrerequisite extends AbstractNestedDocument {
  private static final long serialVersionUID = -8818996089059444041L;

  private String type;

  public HasPrerequisite() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
