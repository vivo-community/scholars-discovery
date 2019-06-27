package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class OutputOfProcessOrEvent extends AbstractNestedDocument {
  private static final long serialVersionUID = -4792766287736854611L;

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
