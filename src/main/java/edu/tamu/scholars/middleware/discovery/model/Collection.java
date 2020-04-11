package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.solr.client.solrj.beans.Field;
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
@CollectionSource(name = "collections", predicate = "http://purl.org/ontology/bibo/Collection")
public class Collection extends Common {

    @Indexed(type = "sorting_string", copyTo = "_text_")
    @PropertySource(template = "collection/name", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String name;

    @Field("abstract")
    @JsonProperty("abstract")
    @Indexed(type = "whole_string", value = "abstract", copyTo = "_text_")
    @PropertySource(template = "collection/abstract", predicate = "http://purl.org/ontology/bibo/abstract")
    private String abstractText;

    @Indexed(type = "whole_string")
    @PropertySource(template = "collection/abbreviation", predicate = "http://vivoweb.org/ontology/core#abbreviation")
    private String abbreviation;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/publicationVenueFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> publicationVenueFor;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "authorType", key = "type"), @Reference(value = "authorRank", key = "rank"), @Reference(value = "authorOrganization", key = "organizations") })
    @PropertySource(template = "collection/author", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> authors;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/authorType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> authorType;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/authorRank", predicate = "http://vivoweb.org/ontology/core#rank")
    private List<String> authorRank;

    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/authorOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> authorOrganization;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "editorType", key = "type"), @Reference(value = "editorRank", key = "rank") })
    @PropertySource(template = "collection/editor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> editors;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/editorType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> editorType;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/editorRank", predicate = "http://vivoweb.org/ontology/core#rank")
    private List<String> editorRank;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "translatorType", key = "type") })
    @PropertySource(template = "collection/translator", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> translators;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/translatorType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> translatorType;

    @Indexed(type = "pdate", searchable = false)
    @PropertySource(template = "collection/publicationDate", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String publicationDate;

    @Indexed(type = "nested_string", searchable = false)
    @NestedObject(properties = { @Reference(value = "publisherType", key = "type") })
    @PropertySource(template = "collection/publisher", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private String publisher;

    @Indexed(type = "nested_string", searchable = false)
    @PropertySource(template = "collection/publisherType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private String publisherType;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/hasSubjectArea", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> subjectAreas;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "featureType", key = "type") })
    @PropertySource(template = "collection/feature", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> features;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/featureType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> featureType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "geographicFocusType", key = "type") })
    @PropertySource(template = "collection/geographicFocus", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> geographicFocus;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/geographicFocusType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> geographicFocusType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "outputOfProcessOrEventType", key = "type") })
    @PropertySource(template = "collection/outputOfProcessOrEvent", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> outputOfProcessOrEvent;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/outputOfProcessOrEventType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> outputOfProcessOrEventType;

    @Indexed(type = "whole_strings", copyTo = "_text_")
    @PropertySource(template = "collection/keyword", predicate = "http://vivoweb.org/ontology/core#freetextKeyword")
    private List<String> keywords;

    @Indexed(type = "whole_string")
    @PropertySource(template = "collection/issn", predicate = "http://purl.org/ontology/bibo/issn")
    private String issn;

    @Indexed(type = "whole_string")
    @PropertySource(template = "collection/eissn", predicate = "http://purl.org/ontology/bibo/eissn")
    private String eissn;

    @Indexed(type = "whole_string")
    @PropertySource(template = "collection/doi", predicate = "http://purl.org/ontology/bibo/doi")
    private String doi;

    @Indexed(type = "whole_string")
    @PropertySource(template = "collection/oclcnum", predicate = "http://purl.org/ontology/bibo/oclcnum")
    private String oclcnum;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "isAboutType", key = "type") })
    @PropertySource(template = "collection/isAbout", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> isAbout;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/isAboutType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> isAboutType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "specifiedOutputOfType", key = "type") })
    @PropertySource(template = "collection/specifiedOutputOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> specifiedOutputOf;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/specifiedOutputOfType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> specifiedOutputOfType;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "mentionType", key = "type") })
    @PropertySource(template = "collection/mention", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> mentions;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/mentionType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> mentionType;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/participatesIn", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> participatesIn;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "supportedByType", key = "type") })
    @PropertySource(template = "collection/supportedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> supportedBy;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "collection/supportedByType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> supportedByType;

    public Collection() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<String> getPublicationVenueFor() {
        return publicationVenueFor;
    }

    public void setPublicationVenueFor(List<String> publicationVenueFor) {
        this.publicationVenueFor = publicationVenueFor;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getAuthorType() {
        return authorType;
    }

    public void setAuthorType(List<String> authorType) {
        this.authorType = authorType;
    }

    public List<String> getAuthorRank() {
        return authorRank;
    }

    public void setAuthorRank(List<String> authorRank) {
        this.authorRank = authorRank;
    }

    public List<String> getAuthorOrganization() {
        return authorOrganization;
    }

    public void setAuthorOrganization(List<String> authorOrganization) {
        this.authorOrganization = authorOrganization;
    }

    public List<String> getEditors() {
        return editors;
    }

    public void setEditors(List<String> editors) {
        this.editors = editors;
    }

    public List<String> getEditorType() {
        return editorType;
    }

    public void setEditorType(List<String> editorType) {
        this.editorType = editorType;
    }

    public List<String> getEditorRank() {
        return editorRank;
    }

    public void setEditorRank(List<String> editorRank) {
        this.editorRank = editorRank;
    }

    public List<String> getTranslators() {
        return translators;
    }

    public void setTranslators(List<String> translators) {
        this.translators = translators;
    }

    public List<String> getTranslatorType() {
        return translatorType;
    }

    public void setTranslatorType(List<String> translatorType) {
        this.translatorType = translatorType;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherType() {
        return publisherType;
    }

    public void setPublisherType(String publisherType) {
        this.publisherType = publisherType;
    }

    public List<String> getSubjectAreas() {
        return subjectAreas;
    }

    public void setSubjectAreas(List<String> subjectAreas) {
        this.subjectAreas = subjectAreas;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public List<String> getFeatureType() {
        return featureType;
    }

    public void setFeatureType(List<String> featureType) {
        this.featureType = featureType;
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

    public List<String> getOutputOfProcessOrEvent() {
        return outputOfProcessOrEvent;
    }

    public void setOutputOfProcessOrEvent(List<String> outputOfProcessOrEvent) {
        this.outputOfProcessOrEvent = outputOfProcessOrEvent;
    }

    public List<String> getOutputOfProcessOrEventType() {
        return outputOfProcessOrEventType;
    }

    public void setOutputOfProcessOrEventType(List<String> outputOfProcessOrEventType) {
        this.outputOfProcessOrEventType = outputOfProcessOrEventType;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getEissn() {
        return eissn;
    }

    public void setEissn(String eissn) {
        this.eissn = eissn;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getOclcnum() {
        return oclcnum;
    }

    public void setOclcnum(String oclcnum) {
        this.oclcnum = oclcnum;
    }

    public List<String> getIsAbout() {
        return isAbout;
    }

    public void setIsAbout(List<String> isAbout) {
        this.isAbout = isAbout;
    }

    public List<String> getIsAboutType() {
        return isAboutType;
    }

    public void setIsAboutType(List<String> isAboutType) {
        this.isAboutType = isAboutType;
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

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public List<String> getMentionType() {
        return mentionType;
    }

    public void setMentionType(List<String> mentionType) {
        this.mentionType = mentionType;
    }

    public List<String> getParticipatesIn() {
        return participatesIn;
    }

    public void setParticipatesIn(List<String> participatesIn) {
        this.participatesIn = participatesIn;
    }

    public List<String> getSupportedBy() {
        return supportedBy;
    }

    public void setSupportedBy(List<String> supportedBy) {
        this.supportedBy = supportedBy;
    }

    public List<String> getSupportedByType() {
        return supportedByType;
    }

    public void setSupportedByType(List<String> supportedByType) {
        this.supportedByType = supportedByType;
    }

}
