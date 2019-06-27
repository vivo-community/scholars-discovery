package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class ProvidesFundingFor extends AbstractNestedDocument {
  private static final long serialVersionUID = 1739501415015608566L;

  private String type;

  public ProvidesFundingFor() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
