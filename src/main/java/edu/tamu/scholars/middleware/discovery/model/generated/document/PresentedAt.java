package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class PresentedAt extends AbstractNestedDocument {
  private static final long serialVersionUID = -1054587688L;

  private String type;

  public PresentedAt() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
