package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class IsAbout extends AbstractNestedDocument {
  private static final long serialVersionUID = 7791815247558628164L;

  private String type;

  public IsAbout() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
