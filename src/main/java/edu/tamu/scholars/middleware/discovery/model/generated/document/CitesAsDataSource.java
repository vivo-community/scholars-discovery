package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class CitesAsDataSource extends AbstractNestedDocument {
  private static final long serialVersionUID = -1770394729142024524L;

  private String type;

  public CitesAsDataSource() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
