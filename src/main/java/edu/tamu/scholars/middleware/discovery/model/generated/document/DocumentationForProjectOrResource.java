package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class DocumentationForProjectOrResource extends AbstractNestedDocument {
  private static final long serialVersionUID = -1918633658L;

  private String type;

  public DocumentationForProjectOrResource() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
