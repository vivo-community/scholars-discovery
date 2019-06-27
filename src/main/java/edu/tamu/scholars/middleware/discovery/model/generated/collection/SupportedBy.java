package edu.tamu.scholars.middleware.discovery.model.generated.collection;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class SupportedBy extends AbstractNestedDocument {
  private static final long serialVersionUID = -4100838900378305273L;

  private String type;

  public SupportedBy() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
