package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.COLLECTION;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.CollectionTarget;
import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.FieldSource;
import edu.tamu.scholars.middleware.discovery.annotation.FieldType;

@JsonInclude(NON_EMPTY)
@CollectionTarget(name = COLLECTION)
@CollectionSource(name = "concepts", predicate = "http://www.w3.org/2004/02/skos/core#Concept")
public class Concept extends Common {

    @Field
    @FieldType(type = "tokenized_string", copyTo = { "_text_", "name_sort" })
    @FieldSource(template = "concept/name", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String name;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings")
    @FieldSource(template = "concept/associatedDepartment", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> associatedDepartments;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", copyTo = "_text_")
    @FieldSource(template = "concept/researchAreaOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> researchAreaOf;

    @Field
    @FieldType(type = "nested_whole_strings", copyTo = "_text_")
    @NestedObject(properties = { @Reference(value = "awardOrHonorForType", key = "type") })
    @FieldSource(template = "concept/awardOrHonorFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> awardOrHonorFor;

    @Field
    @FieldType(type = "nested_whole_strings")
    @FieldSource(template = "concept/awardOrHonorForType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> awardOrHonorForType;

    @Field
    @FieldType(type = "nested_tokenized_strings", copyTo = { "_text_", "awardConferredBy_nested_facets" })
    @NestedObject(properties = { @Reference(value = "awardConferredByType", key = "type") })
    @FieldSource(template = "concept/awardConferredBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> awardConferredBy;

    @Field
    @FieldType(type = "nested_whole_strings")
    @FieldSource(template = "concept/awardConferredByType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> awardConferredByType;

    @Field
    @FieldType(type = "whole_strings", copyTo = "_text_")
    @FieldSource(template = "concept/awardConferredByPreferredLabel", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#awardConferredBy_label")
    private List<String> awardConferredByPreferredLabel;

    @Field
    @FieldType(type = "pdate")
    @FieldSource(template = "concept/yearAwarded", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String yearAwarded;

    @Field
    @FieldType(type = "nested_whole_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "receiptRecipientName", key = "recipientName") })
    @FieldSource(template = "concept/receipts", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receipts;

    @Field
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "concept/receiptRecipientName", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receiptRecipientName;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "concept/broaderConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> broaderConcepts;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "concept/narrowerConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> narrowerConcepts;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "concept/relatedConcept", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> relatedConcepts;

    @Field
    @FieldType(type = "nested_tokenized_string", copyTo = { "_text_", "futureResearchIdeaOf_nested_facets" })
    @NestedObject(properties = { @Reference(value = "futureResearchIdeaOfEmail", key = "email"), @Reference(value = "futureResearchIdeaOfTitle", key = "title"), @Reference(value = "futureResearchIdeaOfOrganization", key = "organizations") })
    @FieldSource(template = "concept/futureResearchIdeaOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String futureResearchIdeaOf;

    @Field
    @FieldType(type = "nested_whole_string")
    @FieldSource(template = "concept/futureResearchIdeaOfEmail", predicate = "http://www.w3.org/2006/vcard/ns#email")
    private String futureResearchIdeaOfEmail;

    @Field
    @FieldType(type = "nested_whole_string")
    @FieldSource(template = "concept/futureResearchIdeaOfTitle", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String futureResearchIdeaOfTitle;

    @Field
    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @FieldType(type = "nested_whole_strings")
    @FieldSource(template = "concept/futureResearchIdeaOfOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> futureResearchIdeaOfOrganization;

    @Field
    @FieldType(type = "whole_strings", copyTo = "_text_")
    @FieldSource(template = "concept/keyword", predicate = "http://vivoweb.org/ontology/core#freetextKeyword")
    private List<String> keywords;

    @Field
    @FieldType(type = "tokenized_string", copyTo = "_text_")
    @FieldSource(template = "concept/description", predicate = "http://vivoweb.org/ontology/core#description")
    private String description;

    @Field
    @FieldType(type = "pdate")
    @FieldSource(template = "concept/createdDate", predicate = "http://vivoweb.org/ontology/core#dateTime")
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

    public String getFutureResearchIdeaOf() {
        return futureResearchIdeaOf;
    }

    public void setFutureResearchIdeaOf(String futureResearchIdeaOf) {
        this.futureResearchIdeaOf = futureResearchIdeaOf;
    }

    public String getFutureResearchIdeaOfEmail() {
        return futureResearchIdeaOfEmail;
    }

    public void setFutureResearchIdeaOfEmail(String futureResearchIdeaOfEmail) {
        this.futureResearchIdeaOfEmail = futureResearchIdeaOfEmail;
    }

    public String getFutureResearchIdeaOfTitle() {
        return futureResearchIdeaOfTitle;
    }

    public void setFutureResearchIdeaOfTitle(String futureResearchIdeaOfTitle) {
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
