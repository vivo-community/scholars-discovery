package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import io.leangen.graphql.annotations.GraphQLIgnore;

@GraphQLIgnore
@JsonInclude(NON_EMPTY)
@SolrDocument(collection = "scholars-discovery")
@CollectionSource(name = "concepts", predicate = "http://www.w3.org/2004/02/skos/core#Concept")
public class Concept extends Common {

    @Indexed(type = "sorting_string", copyTo = "_text_")
    @PropertySource(template = "concept/name", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String name;

    @NestedObject
    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/associatedDepartment", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> associatedDepartments;

    @NestedObject
    @Indexed(type = "nested_strings", copyTo = "_text_")
    @PropertySource(template = "concept/researchAreaOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> researchAreaOf;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "receiptRecipientName", key = "recipientName") })
    @PropertySource(template = "concept/receipts", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receipts;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "concept/receiptRecipientName", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receiptRecipientName;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "concept/broaderConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> broaderConcepts;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "concept/narrowerConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> narrowerConcepts;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "concept/relatedConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> relatedConcepts;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "futureResearchIdeaOfTitle", key = "title"), @Reference(value = "futureResearchIdeaOfOrganization", key = "organizations") })
    @PropertySource(template = "concept/futureResearchIdeaOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> futureResearchIdeaOf;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/futureResearchIdeaOfTitle", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> futureResearchIdeaOfTitle;

    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/futureResearchIdeaOfOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> futureResearchIdeaOfOrganization;

    @Indexed(type = "whole_strings", copyTo = "_text_")
    @PropertySource(template = "concept/keyword", predicate = "http://vivoweb.org/ontology/core#freetextKeyword")
    private List<String> keywords;

    @Indexed(type = "whole_string", copyTo = "_text_")
    @PropertySource(template = "concept/description", predicate = "http://vivoweb.org/ontology/core#description")
    private String description;

    @Indexed(type = "pdate")
    @PropertySource(template = "concept/createdDate", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String createdDate;

    public Concept() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAssociatedDepartments() {
        return associatedDepartments;
    }

    public void setAssociatedDepartments(List<String> associatedDepartments) {
        this.associatedDepartments = associatedDepartments;
    }

    public List<String> getResearchAreaOf() {
        return researchAreaOf;
    }

    public void setResearchAreaOf(List<String> researchAreaOf) {
        this.researchAreaOf = researchAreaOf;
    }

    public List<String> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<String> receipts) {
        this.receipts = receipts;
    }

    public List<String> getReceiptRecipientName() {
        return receiptRecipientName;
    }

    public void setReceiptRecipientName(List<String> receiptRecipientName) {
        this.receiptRecipientName = receiptRecipientName;
    }

    public List<String> getBroaderConcepts() {
        return broaderConcepts;
    }

    public void setBroaderConcepts(List<String> broaderConcepts) {
        this.broaderConcepts = broaderConcepts;
    }

    public List<String> getNarrowerConcepts() {
        return narrowerConcepts;
    }

    public void setNarrowerConcepts(List<String> narrowerConcepts) {
        this.narrowerConcepts = narrowerConcepts;
    }

    public List<String> getRelatedConcepts() {
        return relatedConcepts;
    }

    public void setRelatedConcepts(List<String> relatedConcepts) {
        this.relatedConcepts = relatedConcepts;
    }

    public List<String> getFutureResearchIdeaOf() {
        return futureResearchIdeaOf;
    }

    public void setFutureResearchIdeaOf(List<String> futureResearchIdeaOf) {
        this.futureResearchIdeaOf = futureResearchIdeaOf;
    }

    public List<String> getFutureResearchIdeaOfTitle() {
        return futureResearchIdeaOfTitle;
    }

    public void setFutureResearchIdeaOfTitle(List<String> futureResearchIdeaOfTitle) {
        this.futureResearchIdeaOfTitle = futureResearchIdeaOfTitle;
    }

    public List<String> getFutureResearchIdeaOfOrganization() {
        return futureResearchIdeaOfOrganization;
    }

    public void setFutureResearchIdeaOfOrganization(List<String> futureResearchIdeaOfOrganization) {
        this.futureResearchIdeaOfOrganization = futureResearchIdeaOfOrganization;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
