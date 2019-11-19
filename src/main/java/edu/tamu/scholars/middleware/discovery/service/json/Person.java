package edu.tamu.scholars.middleware.discovery.service.json;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonInclude;

import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;

import io.leangen.graphql.annotations.GraphQLIgnore;

import edu.tamu.scholars.middleware.discovery.model.Common;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;

// annotation like this to help nesting?
/*
    public @interface Reference {
        String value();
        String key();
    }


public class Organization extends AbstractNestedDocument {
  private static final long serialVersionUID = 884563225L;

  private List<Degree> degrees;
  
  public class Relationship extends AbstractNestedDocument {
  private static final long serialVersionUID = -556210658L;

  private List<Organization> organization;

import edu.tamu.scholars.middleware.graphql.model.Person
// read in from Json <- 
//
    private List<Relationship> positions;



    // convert Person.position(s) to
    // PersonIndexDocument -> position(s), positionType(s), positionOrganization(s) etc...


    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "positionType", key = "type"), @Reference(value = "positionOrganization", key = "organizations") })
    @PropertySource(template = "person/position", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> positions;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "person/positionType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> positionType;

    @NestedMultiValuedProperty
    @Indexed(type = "nested_strings")
    @NestedObject(root = false, properties = { @Reference(value = "positionOrganizationParent", key = "parent") })
    @PropertySource(template = "person/positionOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private List<String> positionOrganization;

    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @Indexed(type = "nested_strings")
    @PropertySource(template = "person/positionOrganizationParent", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> positionOrganizationParent;


*/
@GraphQLIgnore
@JsonInclude(NON_EMPTY)
@SolrDocument(collection = "scholars-discovery")
public class Person extends AbstractIndexDocument /* or Common*/ {

    @Indexed(type = "sorting_string", copyTo = "_text_")
    //@PropertySource(template = "person/name", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String name;

    @Indexed(type = "whole_string")
    //@PropertySource(template = "person/primaryEmail", predicate = "http://www.w3.org/2006/vcard/ns#email")
    private String primaryEmail;

    @Indexed(type = "whole_string")
    //@PropertySource(template = "person/preferredTitle", predicate = "http://www.w3.org/2006/vcard/ns#title")
    private String preferredTitle;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "positionType", key = "type"), @Reference(value = "positionOrganization", key = "organizations") })
    //@PropertySource(template = "person/position", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> positions;

    @Indexed(type = "nested_strings")
    //@PropertySource(template = "person/positionType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> positionType;

    @NestedMultiValuedProperty
    @Indexed(type = "nested_strings")
    @NestedObject(root = false, properties = { @Reference(value = "positionOrganizationParent", key = "parent") })
    //@PropertySource(template = "person/positionOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private List<String> positionOrganization;

    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @Indexed(type = "nested_strings")
    //@PropertySource(template = "person/positionOrganizationParent", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> positionOrganizationParent;

    public Person() {
       //
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getPreferredTitle() {
        return preferredTitle;
    }

    public void setPreferredTitle(String preferredTitle) {
        this.preferredTitle = preferredTitle;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public List<String> getPositionType() {
        return positionType;
    }

    public void setPositionType(List<String> positionType) {
        this.positionType = positionType;
    }

    public List<String> getPositionOrganization() {
        return positionOrganization;
    }

    public void setPositionOrganization(List<String> positionOrganization) {
        this.positionOrganization = positionOrganization;
    }

    public List<String> getPositionOrganizationParent() {
        return positionOrganizationParent;
    }

    public void setPositionOrganizationParent(List<String> positionOrganizationParent) {
        this.positionOrganizationParent = positionOrganizationParent;
    }
}
