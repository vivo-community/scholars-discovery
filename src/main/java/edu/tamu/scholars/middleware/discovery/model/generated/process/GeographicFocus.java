package edu.tamu.scholars.middleware.discovery.model.generated.process;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class GeographicFocus extends AbstractNestedDocument {
  private static final long serialVersionUID = 3775299412051354119L;

  private String type;

  public GeographicFocus() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
