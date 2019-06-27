package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class AwardConferredBy extends AbstractNestedDocument {
  private static final long serialVersionUID = 4972280739706475643L;

  private String type;

  public AwardConferredBy() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
