package edu.tamu.scholars.middleware.graphql.model.relationship;

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
    name = "RelationshipSupportedPublicationOrOtherWork"
)
@JsonInclude(NON_EMPTY)
public class SupportedPublicationOrOtherWork extends AbstractNestedDocument {
  private static final long serialVersionUID = -1082844144L;

  private String label;

  private String type;

  public SupportedPublicationOrOtherWork() {
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
}
