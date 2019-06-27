package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;
import java.util.List;

public class EtdChairedBy extends AbstractNestedDocument {
  private static final long serialVersionUID = -313345925L;

  private String email;

  private List<EtdChairedByOrganization> organization;

  public EtdChairedBy() {
    super();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<EtdChairedByOrganization> getOrganization() {
    return organization;
  }

  public void setOrganization(List<EtdChairedByOrganization> organization) {
    this.organization = organization;
  }
}
