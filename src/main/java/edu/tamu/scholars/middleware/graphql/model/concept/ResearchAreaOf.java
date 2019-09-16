package edu.tamu.scholars.middleware.graphql.model.concept;

import edu.tamu.scholars.middleware.graphql.model.concept.ResearchAreaOfOrganization;

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
    name = "ConceptResearchAreaOf"
)
@JsonInclude(NON_EMPTY)
public class ResearchAreaOf extends AbstractNestedDocument {
  private static final long serialVersionUID = 1209049451L;

  private String label;

  private String title;

  private List<ResearchAreaOfOrganization> organizations;

  public ResearchAreaOf() {
    super();
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
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
