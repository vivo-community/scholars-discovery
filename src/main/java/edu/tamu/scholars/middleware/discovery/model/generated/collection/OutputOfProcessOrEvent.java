package edu.tamu.scholars.middleware.discovery.model.generated.collection;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class OutputOfProcessOrEvent extends AbstractNestedDocument {
  private static final long serialVersionUID = -4434556180227521724L;

  private String type;

  public OutputOfProcessOrEvent() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
