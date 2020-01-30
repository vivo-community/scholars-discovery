package edu.tamu.scholars.middleware.graphql.inert.model.person;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import edu.tamu.scholars.middleware.discovery.model.Common;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import io.leangen.graphql.annotations.types.GraphQLType;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonInclude;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;

import edu.tamu.scholars.middleware.graphql.inert.model.position.Organization;
import io.leangen.graphql.annotations.GraphQLIgnore;

// @GraphQLIgnore
@GraphQLType(
  name = "CustomPerson"
)

@JsonInclude(NON_EMPTY)
public class Person extends AbstractNestedDocument {
  private String name;
  private String[] type;
  private PositionStub[] positionStubs;

  public Person() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getType() {
    return this.type;
  }

  public void setType(String[] type) {
    this.type = type;
  }

  @GraphQLIgnore
  public PositionStub[] getPositions() {
    return this.positionStubs;
  }

  @GraphQLIgnore
  public void setPositions(PositionStub[] pos) {
    this.positionStubs = pos;
  }


}
