package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class ContactOrProvidorForService extends AbstractNestedDocument {
  private static final long serialVersionUID = -1283760933L;

  private String type;

  public ContactOrProvidorForService() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
