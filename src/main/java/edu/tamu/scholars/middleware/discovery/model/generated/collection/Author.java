package edu.tamu.scholars.middleware.discovery.model.generated.collection;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;
import java.util.List;

public class Author extends AbstractNestedDocument {
  private static final long serialVersionUID = 1712126685L;

  private String type;

  private String rank;

  private List<String> organizations;

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

  public List<String> getOrganizations() {
    return organizations;
  }

  public void setOrganizations(List<String> organizations) {
    this.organizations = organizations;
  }
}
