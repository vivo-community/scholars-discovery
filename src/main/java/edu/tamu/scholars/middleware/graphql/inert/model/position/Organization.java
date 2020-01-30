package edu.tamu.scholars.middleware.graphql.inert.model.position;

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
// import io.leangen.graphql.annotations.GraphQLIgnore;

// @GraphQLIgnore
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
