package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import io.leangen.graphql.annotations.GraphQLIgnore;

@GraphQLIgnore
@JsonInclude(NON_EMPTY)
@SolrDocument(collection = "scholars-discovery")
@CollectionSource(name = "relationships", predicate = "http://vivoweb.org/ontology/core#Relationship")
public class Relationship extends Common {

    @Indexed(type = "sorting_string", copyTo = "_text_")
    @PropertySource(template = "relationship/title", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String title;

    @Field("abstract")
    @Indexed(type = "whole_string", value = "abstract")
    @JsonProperty("abstract")
    @PropertySource(template = "relationship/abstract", predicate = "http://purl.org/ontology/bibo/abstract")
    private String abstractText;

    @Indexed(type = "whole_string")
    @PropertySource(template = "relationship/description", predicate = "http://vivoweb.org/ontology/core#description")
    private String description;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/organization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> organization;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "receiptOfType", key = "type") })
    @PropertySource(template = "relationship/receiptOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receiptOf;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/receiptOfType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> receiptOfType;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "awardOrHonorForType", key = "type") })
    @PropertySource(template = "relationship/awardOrHonorFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> awardOrHonorFor;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/awardOrHonorForType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> awardOrHonorForType;

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @NestedObject(properties = { @Reference(value = "awardConferredByType", key = "type"), @Reference(value = "awardConferredByAbbreviation", key = "abbreviation"), @Reference(value = "awardConferredByPreferredLabel", key = "preferredLabel") })
    @PropertySource(template = "relationship/awardConferredBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> awardConferredBy;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/awardConferredByType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> awardConferredByType;

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @PropertySource(template = "relationship/awardConferredByAbbreviation", predicate = "http://vivoweb.org/ontology/core#abbreviation")
    private List<String> awardConferredByAbbreviation;

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @PropertySource(template = "relationship/awardConferredByPreferredLabel", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#awardConferredBy_label")
    private List<String> awardConferredByPreferredLabel;

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @NestedObject(properties = { @Reference(value = "awardedByType", key = "type"), @Reference(value = "awardedByAbbreviation", key = "abbreviation"), @Reference(value = "awardedByPreferredLabel", key = "preferredLabel") })
    @PropertySource(template = "relationship/awardedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> awardedBy;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/awardedByType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> awardedByType;

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @PropertySource(template = "relationship/awardedByAbbreviation", predicate = "http://vivoweb.org/ontology/core#abbreviation")
    private List<String> awardedByAbbreviation;

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @PropertySource(template = "relationship/awardedByPreferredLabel", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#awardedBy_label")
    private List<String> awardedByPreferredLabel;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "grantSubcontractedThroughType", key = "type") })
    @PropertySource(template = "relationship/grantSubcontractedThrough", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> grantSubcontractedThrough;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/grantSubcontractedThroughType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> grantSubcontractedThroughType;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "administeredByType", key = "type") })
    @PropertySource(template = "relationship/administeredBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> administeredBy;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/administeredByType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> administeredByType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "geographicFocusType", key = "type") })
    @PropertySource(template = "relationship/geographicFocus", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> geographicFocus;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/geographicFocusType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> geographicFocusType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "subGrantType", key = "type") })
    @PropertySource(template = "relationship/subGrant", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> subGrant;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/subGrantType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> subGrantType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "subGrantOfType", key = "type") })
    @PropertySource(template = "relationship/subGrantOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> subGrantOf;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/subGrantOfType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> subGrantOfType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "providesFundingForType", key = "type") })
    @PropertySource(template = "relationship/providesFundingFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> providesFundingFor;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/providesFundingForType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> providesFundingForType;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "relationship/totalAwardAmount", predicate = "http://vivoweb.org/ontology/core#totalAwardAmount")
    private String totalAwardAmount;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "relationship/directCosts", predicate = "http://vivoweb.org/ontology/core#directCosts")
    private String directCosts;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "relationship/sponsorAwardId", predicate = "http://vivoweb.org/ontology/core#sponsorAwardId")
    private String sponsorAwardId;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "relationship/localAwardId", predicate = "http://vivoweb.org/ontology/core#localAwardId")
    private String localAwardId;

    @Indexed(type = "nested_strings", copyTo = "_text_")
    @NestedObject(properties = { @Reference(value = "contributorType", key = "type"), @Reference(value = "contributorRole", key = "role") })
    @PropertySource(template = "relationship/contributor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> contributors;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "relationship/contributorRole", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> contributorRole;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "relationship/contributorType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> contributorType;

    @NestedObject
    @Indexed(type = "nested_strings", copyTo = "_text_")
    @PropertySource(template = "relationship/principalInvestigator", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> principalInvestigators;

    @NestedObject
    @Indexed(type = "nested_strings", copyTo = "_text_")
    @PropertySource(template = "relationship/coPrincipalInvestigator", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> coPrincipalInvestigators;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "supportedPublicationOrOtherWorkType", key = "type") })
    @PropertySource(template = "relationship/supportedPublicationOrOtherWork", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> supportedPublicationOrOtherWork;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/supportedPublicationOrOtherWorkType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> supportedPublicationOrOtherWorkType;

    @Indexed(type = "pdate")
    @PropertySource(template = "relationship/dateTimeIntervalStart", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateTimeIntervalStart;

    @Indexed(type = "pdate")
    @PropertySource(template = "relationship/dateTimeIntervalEnd", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateTimeIntervalEnd;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "subjectAreaType", key = "type") })
    @PropertySource(template = "relationship/hasSubjectArea", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> subjectAreas;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/hasSubjectAreaType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> subjectAreaType;

    @Indexed(type = "pdate")
    @PropertySource(template = "relationship/yearAwarded", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String yearAwarded;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "inheresInType", key = "type") })
    @PropertySource(template = "relationship/inheresIn", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> inheresIn;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/inheresInType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> inheresInType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "specifiedOutputOfType", key = "type") })
    @PropertySource(template = "relationship/isSpecifiedOutputOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> specifiedOutputOf;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/isSpecifiedOutputOfType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> specifiedOutputOfType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "outputOfType", key = "type") })
    @PropertySource(template = "relationship/outputOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> outputOf;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/outputOfType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> outputOfType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "participatesInType", key = "type") })
    @PropertySource(template = "relationship/participatesIn", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> participatesIn;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "relationship/participatesInType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> participatesInType;

    public Relationship() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOrganization() {
        return organization;
    }

    public void setOrganization(List<String> organization) {
        this.organization = organization;
    }

    public List<String> getReceiptOf() {
        return receiptOf;
    }

    public void setReceiptOf(List<String> receiptOf) {
        this.receiptOf = receiptOf;
    }

    public List<String> getReceiptOfType() {
        return receiptOfType;
    }

    public void setReceiptOfType(List<String> receiptOfType) {
        this.receiptOfType = receiptOfType;
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

    public List<String> getAwardConferredByAbbreviation() {
        return awardConferredByAbbreviation;
    }

    public void setAwardConferredByAbbreviation(List<String> awardConferredByAbbreviation) {
        this.awardConferredByAbbreviation = awardConferredByAbbreviation;
    }

    public List<String> getAwardConferredByPreferredLabel() {
        return awardConferredByPreferredLabel;
    }

    public void setAwardConferredByPreferredLabel(List<String> awardConferredByPreferredLabel) {
        this.awardConferredByPreferredLabel = awardConferredByPreferredLabel;
    }

    public List<String> getAwardedBy() {
        return awardedBy;
    }

    public void setAwardedBy(List<String> awardedBy) {
        this.awardedBy = awardedBy;
    }

    public List<String> getAwardedByType() {
        return awardedByType;
    }

    public void setAwardedByType(List<String> awardedByType) {
        this.awardedByType = awardedByType;
    }

    public List<String> getAwardedByAbbreviation() {
        return awardedByAbbreviation;
    }

    public void setAwardedByAbbreviation(List<String> awardedByAbbreviation) {
        this.awardedByAbbreviation = awardedByAbbreviation;
    }

    public List<String> getAwardedByPreferredLabel() {
        return awardedByPreferredLabel;
    }

    public void setAwardedByPreferredLabel(List<String> awardedByPreferredLabel) {
        this.awardedByPreferredLabel = awardedByPreferredLabel;
    }

    public List<String> getGrantSubcontractedThrough() {
        return grantSubcontractedThrough;
    }

    public void setGrantSubcontractedThrough(List<String> grantSubcontractedThrough) {
        this.grantSubcontractedThrough = grantSubcontractedThrough;
    }

    public List<String> getGrantSubcontractedThroughType() {
        return grantSubcontractedThroughType;
    }

    public void setGrantSubcontractedThroughType(List<String> grantSubcontractedThroughType) {
        this.grantSubcontractedThroughType = grantSubcontractedThroughType;
    }

    public List<String> getAdministeredBy() {
        return administeredBy;
    }

    public void setAdministeredBy(List<String> administeredBy) {
        this.administeredBy = administeredBy;
    }

    public List<String> getAdministeredByType() {
        return administeredByType;
    }

    public void setAdministeredByType(List<String> administeredByType) {
        this.administeredByType = administeredByType;
    }

    public List<String> getGeographicFocus() {
        return geographicFocus;
    }

    public void setGeographicFocus(List<String> geographicFocus) {
        this.geographicFocus = geographicFocus;
    }

    public List<String> getGeographicFocusType() {
        return geographicFocusType;
    }

    public void setGeographicFocusType(List<String> geographicFocusType) {
        this.geographicFocusType = geographicFocusType;
    }

    public List<String> getSubGrant() {
        return subGrant;
    }

    public void setSubGrant(List<String> subGrant) {
        this.subGrant = subGrant;
    }

    public List<String> getSubGrantType() {
        return subGrantType;
    }

    public void setSubGrantType(List<String> subGrantType) {
        this.subGrantType = subGrantType;
    }

    public List<String> getSubGrantOf() {
        return subGrantOf;
    }

    public void setSubGrantOf(List<String> subGrantOf) {
        this.subGrantOf = subGrantOf;
    }

    public List<String> getSubGrantOfType() {
        return subGrantOfType;
    }

    public void setSubGrantOfType(List<String> subGrantOfType) {
        this.subGrantOfType = subGrantOfType;
    }

    public List<String> getProvidesFundingFor() {
        return providesFundingFor;
    }

    public void setProvidesFundingFor(List<String> providesFundingFor) {
        this.providesFundingFor = providesFundingFor;
    }

    public List<String> getProvidesFundingForType() {
        return providesFundingForType;
    }

    public void setProvidesFundingForType(List<String> providesFundingForType) {
        this.providesFundingForType = providesFundingForType;
    }

    public String getTotalAwardAmount() {
        return totalAwardAmount;
    }

    public void setTotalAwardAmount(String totalAwardAmount) {
        this.totalAwardAmount = totalAwardAmount;
    }

    public String getDirectCosts() {
        return directCosts;
    }

    public void setDirectCosts(String directCosts) {
        this.directCosts = directCosts;
    }

    public String getSponsorAwardId() {
        return sponsorAwardId;
    }

    public void setSponsorAwardId(String sponsorAwardId) {
        this.sponsorAwardId = sponsorAwardId;
    }

    public String getLocalAwardId() {
        return localAwardId;
    }

    public void setLocalAwardId(String localAwardId) {
        this.localAwardId = localAwardId;
    }

    public List<String> getContributors() {
        return contributors;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }

    public List<String> getContributorRole() {
        return contributorRole;
    }

    public void setContributorRole(List<String> contributorRole) {
        this.contributorRole = contributorRole;
    }

    public List<String> getContributorType() {
        return contributorType;
    }

    public void setContributorType(List<String> contributorType) {
        this.contributorType = contributorType;
    }

    public List<String> getPrincipalInvestigators() {
        return principalInvestigators;
    }

    public void setPrincipalInvestigators(List<String> principalInvestigators) {
        this.principalInvestigators = principalInvestigators;
    }

    public List<String> getCoPrincipalInvestigators() {
        return coPrincipalInvestigators;
    }

    public void setCoPrincipalInvestigators(List<String> coPrincipalInvestigators) {
        this.coPrincipalInvestigators = coPrincipalInvestigators;
    }

    public List<String> getSupportedPublicationOrOtherWork() {
        return supportedPublicationOrOtherWork;
    }

    public void setSupportedPublicationOrOtherWork(List<String> supportedPublicationOrOtherWork) {
        this.supportedPublicationOrOtherWork = supportedPublicationOrOtherWork;
    }

    public List<String> getSupportedPublicationOrOtherWorkType() {
        return supportedPublicationOrOtherWorkType;
    }

    public void setSupportedPublicationOrOtherWorkType(List<String> supportedPublicationOrOtherWorkType) {
        this.supportedPublicationOrOtherWorkType = supportedPublicationOrOtherWorkType;
    }

    public String getDateTimeIntervalStart() {
        return dateTimeIntervalStart;
    }

    public void setDateTimeIntervalStart(String dateTimeIntervalStart) {
        this.dateTimeIntervalStart = dateTimeIntervalStart;
    }

    public String getDateTimeIntervalEnd() {
        return dateTimeIntervalEnd;
    }

    public void setDateTimeIntervalEnd(String dateTimeIntervalEnd) {
        this.dateTimeIntervalEnd = dateTimeIntervalEnd;
    }

    public List<String> getSubjectAreas() {
        return subjectAreas;
    }

    public void setSubjectAreas(List<String> subjectAreas) {
        this.subjectAreas = subjectAreas;
    }

    public List<String> getSubjectAreaType() {
        return subjectAreaType;
    }

    public void setSubjectAreaType(List<String> subjectAreaType) {
        this.subjectAreaType = subjectAreaType;
    }

    public String getYearAwarded() {
        return yearAwarded;
    }

    public void setYearAwarded(String yearAwarded) {
        this.yearAwarded = yearAwarded;
    }

    public List<String> getInheresIn() {
        return inheresIn;
    }

    public void setInheresIn(List<String> inheresIn) {
        this.inheresIn = inheresIn;
    }

    public List<String> getInheresInType() {
        return inheresInType;
    }

    public void setInheresInType(List<String> inheresInType) {
        this.inheresInType = inheresInType;
    }

    public List<String> getSpecifiedOutputOf() {
        return specifiedOutputOf;
    }

    public void setSpecifiedOutputOf(List<String> specifiedOutputOf) {
        this.specifiedOutputOf = specifiedOutputOf;
    }

    public List<String> getSpecifiedOutputOfType() {
        return specifiedOutputOfType;
    }

    public void setSpecifiedOutputOfType(List<String> specifiedOutputOfType) {
        this.specifiedOutputOfType = specifiedOutputOfType;
    }

    public List<String> getOutputOf() {
        return outputOf;
    }

    public void setOutputOf(List<String> outputOf) {
        this.outputOf = outputOf;
    }

    public List<String> getOutputOfType() {
        return outputOfType;
    }

    public void setOutputOfType(List<String> outputOfType) {
        this.outputOfType = outputOfType;
    }

    public List<String> getParticipatesIn() {
        return participatesIn;
    }

    public void setParticipatesIn(List<String> participatesIn) {
        this.participatesIn = participatesIn;
    }

    public List<String> getParticipatesInType() {
        return participatesInType;
    }

    public void setParticipatesInType(List<String> participatesInType) {
        this.participatesInType = participatesInType;
    }

}
