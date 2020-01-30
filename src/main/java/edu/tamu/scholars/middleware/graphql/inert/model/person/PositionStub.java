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

//import edu.tamu.scholars.middleware.graphql.inert.model.position.Organization;
import edu.tamu.scholars.middleware.graphql.inert.model.position.OrganizationStub;

import io.leangen.graphql.annotations.GraphQLIgnore;

@GraphQLIgnore
@JsonInclude(NON_EMPTY)
public class PositionStub extends AbstractNestedDocument {
  private String id;
  private String label;
  private String[] type;
  private OrganizationStub[] organizations;

  public PositionStub() {
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String[] getType() {
    return this.getType();
  }

  public void setType(String[] type) {
    this.type = type;
  }

  public OrganizationStub[] getOrganizations() {
    return this.organizations;
  }

  public void setOrganizations(OrganizationStub[] orgs) {
    this.organizations = orgs;
  }

}
