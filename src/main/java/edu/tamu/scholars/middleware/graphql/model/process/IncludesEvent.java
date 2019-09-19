package edu.tamu.scholars.middleware.graphql.model.process;

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
    name = "ProcessIncludesEvent"
)
@JsonInclude(NON_EMPTY)
public class IncludesEvent extends AbstractNestedDocument {
  private static final long serialVersionUID = -505289174L;

  private String label;

  public IncludesEvent() {
    super();
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
