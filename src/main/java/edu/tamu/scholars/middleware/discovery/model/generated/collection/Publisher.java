package edu.tamu.scholars.middleware.discovery.model.generated.collection;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class Publisher extends AbstractNestedDocument {
  private static final long serialVersionUID = 9059498041418099074L;

  private String type;

  public Publisher() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
