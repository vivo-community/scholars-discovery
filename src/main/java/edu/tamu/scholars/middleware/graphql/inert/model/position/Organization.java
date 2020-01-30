package edu.tamu.scholars.middleware.graphql.inert.model.position;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import io.leangen.graphql.annotations.types.GraphQLType;

import com.fasterxml.jackson.annotation.JsonInclude;

@GraphQLType(
  name = "CustomOrganization"
)

@JsonInclude(NON_EMPTY)
public class Organization extends AbstractNestedDocument {
  private String name;
  private String[] type;

  public Organization() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getType() {
    return type;
  }

  public void setType(String[] type) {
    this.type = type;
  }

  // TODO: organizationWithin ...
}
