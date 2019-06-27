package edu.tamu.scholars.middleware.discovery.model.generated.concept;

import edu.tamu.scholars.middleware.discovery.model.generated.concept.ResearchAreaOfOrganization;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;
import java.util.List;

public class ResearchAreaOf extends AbstractNestedDocument {
  private static final long serialVersionUID = -1834738045L;

  private String title;

  private List<ResearchAreaOfOrganization> organizations;

  public ResearchAreaOf() {
    super();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<ResearchAreaOfOrganization> getOrganizations() {
    return organizations;
  }

  public void setOrganizations(List<ResearchAreaOfOrganization> organizations) {
    this.organizations = organizations;
  }
}
