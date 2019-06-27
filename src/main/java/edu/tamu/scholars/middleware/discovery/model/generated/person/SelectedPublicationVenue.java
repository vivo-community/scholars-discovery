package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class SelectedPublicationVenue extends AbstractNestedDocument {
  private static final long serialVersionUID = 1587141977L;

  private String type;

  public SelectedPublicationVenue() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
