package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;
import java.util.List;

public class Author extends AbstractNestedDocument {
  private static final long serialVersionUID = 9063549752648290739L;

  private String type;

  private String rank;

  private List<AuthorOrganization> organizations;

  public Author() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public List<AuthorOrganization> getOrganizations() {
    return organizations;
  }

  public void setOrganizations(List<AuthorOrganization> organizations) {
    this.organizations = organizations;
  }
}
