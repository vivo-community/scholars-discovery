package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.util.List;

public class PositionOrganization extends AbstractNestedDocument {
  private static final long serialVersionUID = -1717894281L;

  private List<PositionOrganizationParent> parent;

  public PositionOrganization() {
    super();
  }

  public List<PositionOrganizationParent> getParent() {
    return parent;
  }

  public void setParent(List<PositionOrganizationParent> parent) {
    this.parent = parent;
  }
}
