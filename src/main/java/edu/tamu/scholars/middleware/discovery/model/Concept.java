package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonInclude;

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

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @NestedObject(properties = { @Reference(value = "researchAreaOfTitle", key = "title"), @Reference(value = "researchAreaOfOrganization", key = "organizations") })
    @PropertySource(template = "concept/researchAreaOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> researchAreaOf;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/researchAreaOfTitle", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> researchAreaOfTitle;

    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/researchAreaOfOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> researchAreaOfOrganization;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "awardOrHonorForType", key = "type") })
    @PropertySource(template = "concept/awardOrHonorFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> awardOrHonorFor;

    @Indexed
    @PropertySource(template = "concept/awardOrHonorForType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> awardOrHonorForType;

    @Indexed(type = "nested_string")
    @NestedObject(properties = { @Reference(value = "awardConferredByType", key = "type") })
    @PropertySource(template = "concept/awardConferredBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> awardConferredBy;

    @Indexed(type = "nested_string")
    @PropertySource(template = "concept/awardConferredByType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> awardConferredByType;

    @Indexed(type = "whole_string")
    @PropertySource(template = "concept/awardConferredByPreferredLabel", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#awardConferredBy_label")
    private List<String> awardConferredByPreferredLabel;

    @Indexed(type = "pdate")
    @PropertySource(template = "concept/yearAwarded", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String yearAwarded;

    @NestedObject(properties = { @Reference(value = "receiptRecipientName", key = "recipientName") })
    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/receipts", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receipts;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/receiptRecipientName", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receiptRecipientName;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "broaderConceptType", key = "type") })
    @PropertySource(template = "concept/broaderConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> broaderConcepts;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/broaderConceptType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> broaderConceptType;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "narrowerConceptType", key = "type") })
    @PropertySource(template = "concept/narrowerConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> narrowerConcepts;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/narrowerConceptType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> narrowerConceptType;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "relatedConceptType", key = "type") })
    @PropertySource(template = "concept/relatedConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> relatedConcepts;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/relatedConceptType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> relatedConceptType;

    @NestedObject
    @Indexed(type = "nested_strings")
    @PropertySource(template = "concept/futureResearchIdeaOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> futureResearchIdeaOf;

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

    public List<String> getResearchAreaOfTitle() {
        return researchAreaOfTitle;
    }

    public void setResearchAreaOfTitle(List<String> researchAreaOfTitle) {
        this.researchAreaOfTitle = researchAreaOfTitle;
    }

    public List<String> getResearchAreaOfOrganization() {
        return researchAreaOfOrganization;
    }

    public void setResearchAreaOfOrganization(List<String> researchAreaOfOrganization) {
        this.researchAreaOfOrganization = researchAreaOfOrganization;
    }

    public List<String> getAwardOrHonorFor() {
        return awardOrHonorFor;
    }

    public void setAwardOrHonorFor(List<String> awardOrHonorFor) {
        this.awardOrHonorFor = awardOrHonorFor;
    }

    public List<String> getAwardOrHonorForType() {
        return awardOrHonorForType;
    }

    public void setAwardOrHonorForType(List<String> awardOrHonorForType) {
        this.awardOrHonorForType = awardOrHonorForType;
    }

    public List<String> getAwardConferredBy() {
        return awardConferredBy;
    }

    public void setAwardConferredBy(List<String> awardConferredBy) {
        this.awardConferredBy = awardConferredBy;
    }

    public List<String> getAwardConferredByType() {
        return awardConferredByType;
    }

    public void setAwardConferredByType(List<String> awardConferredByType) {
        this.awardConferredByType = awardConferredByType;
    }

    public List<String> getAwardConferredByPreferredLabel() {
        return awardConferredByPreferredLabel;
    }

    public void setAwardConferredByPreferredLabel(List<String> awardConferredByPreferredLabel) {
        this.awardConferredByPreferredLabel = awardConferredByPreferredLabel;
    }

    public String getYearAwarded() {
        return yearAwarded;
    }

    public void setYearAwarded(String yearAwarded) {
        this.yearAwarded = yearAwarded;
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

    public List<String> getBroaderConceptType() {
        return broaderConceptType;
    }

    public void setBroaderConceptType(List<String> broaderConceptType) {
        this.broaderConceptType = broaderConceptType;
    }

    public List<String> getNarrowerConcepts() {
        return narrowerConcepts;
    }

    public void setNarrowerConcepts(List<String> narrowerConcepts) {
        this.narrowerConcepts = narrowerConcepts;
    }

    public List<String> getNarrowerConceptType() {
        return narrowerConceptType;
    }

    public void setNarrowerConceptType(List<String> narrowerConceptType) {
        this.narrowerConceptType = narrowerConceptType;
    }

    public List<String> getRelatedConcepts() {
        return relatedConcepts;
    }

    public void setRelatedConcepts(List<String> relatedConcepts) {
        this.relatedConcepts = relatedConcepts;
    }

    public List<String> getRelatedConceptType() {
        return relatedConceptType;
    }

    public void setRelatedConceptType(List<String> relatedConceptType) {
        this.relatedConceptType = relatedConceptType;
    }

    public List<String> getFutureResearchIdeaOf() {
        return futureResearchIdeaOf;
    }

    public void setFutureResearchIdeaOf(List<String> futureResearchIdeaOf) {
        this.futureResearchIdeaOf = futureResearchIdeaOf;
    }

}
