package edu.tamu.scholars.middleware.graphql.inert.model.person;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import io.leangen.graphql.annotations.types.GraphQLType;

import com.fasterxml.jackson.annotation.JsonInclude;

import edu.tamu.scholars.middleware.graphql.inert.model.position.OrganizationStub;
import io.leangen.graphql.annotations.GraphQLIgnore;

// @GraphQLIgnore
@GraphQLType(
  name = "CustomPosition"
)

@JsonInclude(NON_EMPTY)
public class Position extends AbstractNestedDocument {
  private String title;
  private String[] type;
  private OrganizationStub[] organizations;

  public Position() {
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String[] getType() {
    return this.type;
  }

  public void setType(String[] type) {
    this.type = type;
  }

  
  @GraphQLIgnore
  public OrganizationStub[] getOrganization() {
    return this.organizations;
  }

  @GraphQLIgnore
  public void setOrganization(OrganizationStub[] orgs) {
    this.organizations = orgs;
  }
  
  
}
