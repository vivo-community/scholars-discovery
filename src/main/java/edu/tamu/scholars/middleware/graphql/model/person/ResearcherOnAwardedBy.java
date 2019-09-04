package edu.tamu.scholars.middleware.graphql.model.person;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import io.leangen.graphql.annotations.types.GraphQLType;
import java.lang.String;

/**
 * This file is automatically generated on compile.
 *
 * Do not modify this file -- YOUR CHANGES WILL BE ERASED!
 */
@GraphQLType(
    name = "PersonResearcherOnAwardedBy"
)
@JsonInclude(NON_EMPTY)
public class ResearcherOnAwardedBy extends AbstractNestedDocument {
  private static final long serialVersionUID = 1426612025L;

  private String preferredLabel;

  public ResearcherOnAwardedBy() {
    super();
  }

  public String getPreferredLabel() {
    return preferredLabel;
  }

  public void setPreferredLabel(String preferredLabel) {
    this.preferredLabel = preferredLabel;
  }
}