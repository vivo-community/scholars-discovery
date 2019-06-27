package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class AwardOrHonorFor extends AbstractNestedDocument {
  private static final long serialVersionUID = 6461682259148997171L;

  private String type;

  public AwardOrHonorFor() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
