package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class AwardedBy extends AbstractNestedDocument {
  private static final long serialVersionUID = -6572850854633172531L;

  private String type;

  public AwardedBy() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
