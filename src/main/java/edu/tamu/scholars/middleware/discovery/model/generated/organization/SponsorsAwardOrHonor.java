package edu.tamu.scholars.middleware.discovery.model.generated.organization;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class SponsorsAwardOrHonor extends AbstractNestedDocument {
  private static final long serialVersionUID = -1307522532367922442L;

  private String type;

  public SponsorsAwardOrHonor() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
