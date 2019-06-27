package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class GrantSubcontractedThrough extends AbstractNestedDocument {
  private static final long serialVersionUID = 772726360L;

  private String type;

  public GrantSubcontractedThrough() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
