package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;
import java.util.List;

public class Position extends AbstractNestedDocument {
  private static final long serialVersionUID = -8003794572799689830L;

  private String type;

  private List<PositionOrganization> organizations;

  public Position() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<PositionOrganization> getOrganizations() {
    return organizations;
  }

  public void setOrganizations(List<PositionOrganization> organizations) {
    this.organizations = organizations;
  }
}
