package edu.tamu.scholars.middleware.graphql.model.person;

import edu.tamu.scholars.middleware.graphql.model.person.PositionOrganization;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import io.leangen.graphql.annotations.types.GraphQLType;
import java.lang.String;
import java.util.List;

/**
 * This file is automatically generated on compile.
 *
 * Do not modify this file -- YOUR CHANGES WILL BE ERASED!
 */
@GraphQLType(
    name = "PersonPosition"
)
@JsonInclude(NON_EMPTY)
public class Position extends AbstractNestedDocument {
  private static final long serialVersionUID = -294827460L;

  private String label;

  private String type;

  private List<PositionOrganization> organizations;

  public Position() {
    super();
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
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
