package edu.tamu.scholars.middleware.graphql.inert.model.position;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.leangen.graphql.annotations.GraphQLIgnore;

@GraphQLIgnore
@JsonInclude(NON_EMPTY)
public class OrganizationStub extends AbstractNestedDocument {
  private String id;
  private String label;
  private String[] type;

  public OrganizationStub() {
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLabel() {
    return this.label;
  }

  public void setTitle(String label) {
    this.label = label;
  }

  public String[] getType() {
    return this.type;
  }

  public void setType(String[] type) {
    this.type = type;
  }

}
