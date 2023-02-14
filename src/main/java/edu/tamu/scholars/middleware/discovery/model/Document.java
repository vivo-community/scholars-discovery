package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.COLLECTION;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.CollectionTarget;
import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.FieldSource;
import edu.tamu.scholars.middleware.discovery.annotation.FieldType;

@JsonInclude(NON_EMPTY)
@CollectionTarget(name = COLLECTION)
@CollectionSource(name = "documents", predicate = "http://purl.org/ontology/bibo/Document")
public class Document extends Common {

    @Field
    @FieldType(type = "tokenized_string", copyTo = { "_text_", "title_sort" })
    @FieldSource(template = "document/title", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String title;

    @Field("abstract")
    @JsonProperty("abstract")
    @FieldType(type = "tokenized_string", value = "abstract", copyTo = "_text_")
    @FieldSource(template = "document/abstract", predicate = "http://purl.org/ontology/bibo/abstract")
    private String abstractText;

    @Field
    @FieldType(type = "whole_string", copyTo = "_text_")
    @FieldSource(template = "document/abbreviation", predicate = "http://vivoweb.org/ontology/core#abbreviation")
    private String abbreviation;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_string", copyTo = "_text_")
    @FieldSource(template = "document/publicationVenue", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private String publicationVenue;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_string", searchable = false)
    @FieldSource(template = "document/hasPublicationVenueFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private String hasPublicationVenueFor;

    @Field
    @FieldType(type = "whole_string", copyTo = "_text_")
    @FieldSource(template = "document/publicationOutlet", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#publishedProceedings", unique = true)
    private String publicationOutlet;

    @Field
    @FieldType(type = "whole_string", copyTo = "_text_")
    @FieldSource(template = "document/nameOfConference", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#nameOfConference", unique = true)
    private String nameOfConference;

    @Field
    @FieldType(type = "nested_whole_strings", copyTo = "_text_")
    @NestedObject(properties = { @Reference(value = "authorOrganization", key = "organizations") })
    @FieldSource(template = "document/author", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> authors;

    @Field
    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @FieldType(type = "nested_whole_strings")
    @FieldSource(template = "document/authorOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> authorOrganization;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/editor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> editors;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/translator", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> translators;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/status", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String status;

    @Field
    @FieldType(type = "pdate")
    @FieldSource(template = "document/publicationDate", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String publicationDate;

    @Field
    @FieldType(type = "nested_whole_string")
    @NestedObject(properties = { @Reference(value = "publisherType", key = "type") })
    @FieldSource(template = "document/publisher", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private String publisher;

    @Field
    @FieldType(type = "nested_whole_string")
    @FieldSource(template = "document/publisherType", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private String publisherType;

    @Field
    @FieldType(type = "pdate", searchable = false)
    @FieldSource(template = "document/dateFiled", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateFiled;

    @Field
    @FieldType(type = "pdate", searchable = false)
    @FieldSource(template = "document/dateIssued", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateIssued;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings")
    @FieldSource(template = "document/hasSubjectArea", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> subjectAreas;

    @Field
    @FieldType(type = "whole_strings", searchable = false)
    @FieldSource(template = "document/hasRestriction", predicate = "http://purl.obolibrary.org/obo/ERO_0000045")
    private List<String> restrictions;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/documentPart", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> documentParts;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/chapter", predicate = "http://purl.org/ontology/bibo/chapter")
    private String chapter;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/feature", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> features;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/edition", predicate = "http://purl.org/ontology/bibo/edition")
    private String edition;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/documentationForProjectOrResource", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> documentationForProjectOrResource;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/outputOfProcessOrEvent", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> outputOfProcessOrEvent;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/presentedAt", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> presentedAt;

    @Field
    @FieldType(type = "whole_strings", copyTo = "_text_")
    @FieldSource(template = "document/keyword", predicate = "http://vivoweb.org/ontology/core#freetextKeyword")
    private List<String> keywords;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/eanucc13", predicate = "http://purl.org/ontology/bibo/eanucc13")
    private String eanucc13;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/nihmsid", predicate = "http://vivoweb.org/ontology/core#nihmsid")
    private String nihmsid;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/pmcid", predicate = "http://vivoweb.org/ontology/core#pmcid")
    private String pmcid;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/identifier", predicate = "http://purl.org/ontology/bibo/identifier")
    private String identifier;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/patentNumber", predicate = "http://vivoweb.org/ontology/core#patentNumber")
    private String patentNumber;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/doi", predicate = "http://purl.org/ontology/bibo/doi")
    private String doi;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/oclcnum", predicate = "http://purl.org/ontology/bibo/oclcnum")
    private String oclcnum;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/isbn10", predicate = "http://purl.org/ontology/bibo/isbn10")
    private String isbn10;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/isbn13", predicate = "http://purl.org/ontology/bibo/isbn13")
    private String isbn13;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/pmid", predicate = "http://purl.org/ontology/bibo/pmid")
    private String pmid;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/lccn", predicate = "http://purl.org/ontology/bibo/lccn")
    private String lccn;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/issn", predicate = "http://purl.org/ontology/bibo/issn")
    private String issn;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/eissn", predicate = "http://purl.org/ontology/bibo/eissn")
    private String eissn;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/uri", predicate = "http://purl.org/ontology/bibo/uri")
    private String uri;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/citedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> citedBy;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/cites", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> cites;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/citesAsDataSource", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> citesAsDataSource;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/hasTranslation", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> translations;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/translationOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> translationOf;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/globalCitationFrequency", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> globalCitationFrequency;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/iclCode", predicate = "http://vivoweb.org/ontology/core#iclCode")
    private String iclCode;

    @Field
    @FieldType(type = "pint")
    @FieldSource(template = "document/numberOfPages", predicate = "http://purl.org/ontology/bibo/numPages")
    private Integer numberOfPages;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/pageStart", predicate = "http://purl.org/ontology/bibo/pageStart")
    private String pageStart;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/pageEnd", predicate = "http://purl.org/ontology/bibo/pageEnd")
    private String pageEnd;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/number", predicate = "http://purl.org/ontology/bibo/number")
    private String number;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/volume", predicate = "http://purl.org/ontology/bibo/volume")
    private String volume;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/issue", predicate = "http://purl.org/ontology/bibo/issue")
    private String issue;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/placeOfPublication", predicate = "http://vivoweb.org/ontology/core#placeOfPublication")
    private String placeOfPublication;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/assignee", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> assignees;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/reproducedIn", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> reproducedIn;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/reproduces", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> reproduces;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/isAbout", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> isAbout;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/specifiedOutputOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> specifiedOutputOf;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "document/isTemplate", predicate = "http://purl.obolibrary.org/obo/ARG_0000001")
    private String isTemplate;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/mention", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> mentions;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/participatesIn", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> participatesIn;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/supportedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> supportedBy;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/receipt", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receipts;

    @Field
    @FieldType(type = "pfloat")
    @FieldSource(template = "document/altmetricScore", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#AltmetricScore")
    private Float altmetricScore;

    @Field
    @FieldType(type = "pint")
    @FieldSource(template = "document/citationCount", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#CitationCount")
    private Integer citationCount;

    @Field
    @FieldType(type = "whole_strings")
    @FieldSource(template = "document/tag", predicate = "http://purl.obolibrary.org/obo/ARG_0000015")
    private List<String> tags;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/note", predicate = "http://www.w3.org/2006/vcard/ns#note")
    private String note;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/key", predicate = "http://www.w3.org/2006/vcard/ns#key")
    private String key;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/url", predicate = "http://www.w3.org/2006/vcard/ns#url")
    private String url;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "document/etdChairedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> etdChairedBy;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings")
    @FieldSource(template = "document/advisedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> advisedBy;

    @Field
    @FieldType(type = "whole_strings")
    @FieldSource(template = "document/completeAuthorList", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#completeAuthorList", split = true)
    private List<String> completeAuthorList;

    @Field
    @FieldType(type = "whole_strings")
    @FieldSource(template = "document/authorList", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#fullAuthorList")
    private List<String> authorList;

    @Field
    @FieldType(type = "whole_strings", searchable = false)
    @FieldSource(template = "document/editorList", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#fullEditorList")
    private List<String> editorList;

    @Field
    @FieldType(type = "tokenized_string", copyTo = "_text_")
    @FieldSource(template = "document/bookTitle", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#bookTitleForChapter")
    private String bookTitle;

    @Field
    @FieldType(type = "whole_string")
    @FieldSource(template = "document/newsOutlet", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#NewsOutlet")
    private String newsOutlet;

    public Document() {

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getPublicationVenue() {
        return publicationVenue;
    }

    public void setPublicationVenue(String publicationVenue) {
        this.publicationVenue = publicationVenue;
    }

    public String getHasPublicationVenueFor() {
        return hasPublicationVenueFor;
    }

    public void setHasPublicationVenueFor(String hasPublicationVenueFor) {
        this.hasPublicationVenueFor = hasPublicationVenueFor;
    }

    public String getPublicationOutlet() {
        return publicationOutlet;
    }

    public void setPublicationOutlet(String publicationOutlet) {
        this.publicationOutlet = publicationOutlet;
    }

    public String getNameOfConference() {
        return nameOfConference;
    }

    public void setNameOfConference(String nameOfConference) {
        this.nameOfConference = nameOfConference;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
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

    public List<String> getTranslators() {
        return translators;
    }

    public void setTranslators(List<String> translators) {
        this.translators = translators;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDateFiled() {
        return dateFiled;
    }

    public void setDateFiled(String dateFiled) {
        this.dateFiled = dateFiled;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public List<String> getSubjectAreas() {
        return subjectAreas;
    }

    public void setSubjectAreas(List<String> subjectAreas) {
        this.subjectAreas = subjectAreas;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public List<String> getDocumentParts() {
        return documentParts;
    }

    public void setDocumentParts(List<String> documentParts) {
        this.documentParts = documentParts;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public List<String> getDocumentationForProjectOrResource() {
        return documentationForProjectOrResource;
    }

    public void setDocumentationForProjectOrResource(List<String> documentationForProjectOrResource) {
        this.documentationForProjectOrResource = documentationForProjectOrResource;
    }

    public List<String> getOutputOfProcessOrEvent() {
        return outputOfProcessOrEvent;
    }

    public void setOutputOfProcessOrEvent(List<String> outputOfProcessOrEvent) {
        this.outputOfProcessOrEvent = outputOfProcessOrEvent;
    }

    public List<String> getPresentedAt() {
        return presentedAt;
    }

    public void setPresentedAt(List<String> presentedAt) {
        this.presentedAt = presentedAt;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getEanucc13() {
        return eanucc13;
    }

    public void setEanucc13(String eanucc13) {
        this.eanucc13 = eanucc13;
    }

    public String getNihmsid() {
        return nihmsid;
    }

    public void setNihmsid(String nihmsid) {
        this.nihmsid = nihmsid;
    }

    public String getPmcid() {
        return pmcid;
    }

    public void setPmcid(String pmcid) {
        this.pmcid = pmcid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPatentNumber() {
        return patentNumber;
    }

    public void setPatentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
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

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getLccn() {
        return lccn;
    }

    public void setLccn(String lccn) {
        this.lccn = lccn;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getCitedBy() {
        return citedBy;
    }

    public void setCitedBy(List<String> citedBy) {
        this.citedBy = citedBy;
    }

    public List<String> getCites() {
        return cites;
    }

    public void setCites(List<String> cites) {
        this.cites = cites;
    }

    public List<String> getCitesAsDataSource() {
        return citesAsDataSource;
    }

    public void setCitesAsDataSource(List<String> citesAsDataSource) {
        this.citesAsDataSource = citesAsDataSource;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public List<String> getTranslationOf() {
        return translationOf;
    }

    public void setTranslationOf(List<String> translationOf) {
        this.translationOf = translationOf;
    }

    public List<String> getGlobalCitationFrequency() {
        return globalCitationFrequency;
    }

    public void setGlobalCitationFrequency(List<String> globalCitationFrequency) {
        this.globalCitationFrequency = globalCitationFrequency;
    }

    public String getIclCode() {
        return iclCode;
    }

    public void setIclCode(String iclCode) {
        this.iclCode = iclCode;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPageStart() {
        return pageStart;
    }

    public void setPageStart(String pageStart) {
        this.pageStart = pageStart;
    }

    public String getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(String pageEnd) {
        this.pageEnd = pageEnd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public List<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<String> assignees) {
        this.assignees = assignees;
    }

    public List<String> getReproducedIn() {
        return reproducedIn;
    }

    public void setReproducedIn(List<String> reproducedIn) {
        this.reproducedIn = reproducedIn;
    }

    public List<String> getReproduces() {
        return reproduces;
    }

    public void setReproduces(List<String> reproduces) {
        this.reproduces = reproduces;
    }

    public List<String> getIsAbout() {
        return isAbout;
    }

    public void setIsAbout(List<String> isAbout) {
        this.isAbout = isAbout;
    }

    public List<String> getSpecifiedOutputOf() {
        return specifiedOutputOf;
    }

    public void setSpecifiedOutputOf(List<String> specifiedOutputOf) {
        this.specifiedOutputOf = specifiedOutputOf;
    }

    public String getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(String isTemplate) {
        this.isTemplate = isTemplate;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
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

    public List<String> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<String> receipts) {
        this.receipts = receipts;
    }

    public Float getAltmetricScore() {
        return altmetricScore;
    }

    public void setAltmetricScore(Float altmetricScore) {
        this.altmetricScore = altmetricScore;
    }

    public Integer getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(Integer citationCount) {
        this.citationCount = citationCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getEtdChairedBy() {
        return etdChairedBy;
    }

    public void setEtdChairedBy(List<String> etdChairedBy) {
        this.etdChairedBy = etdChairedBy;
    }

    public List<String> getAdvisedBy() {
        return advisedBy;
    }

    public void setAdvisedBy(List<String> advisedBy) {
        this.advisedBy = advisedBy;
    }

    public List<String> getCompleteAuthorList() {
        return completeAuthorList;
    }

    public void setCompleteAuthorList(List<String> completeAuthorList) {
        this.completeAuthorList =completeAuthorList;
    }

    public List<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }

    public List<String> getEditorList() {
        return editorList;
    }

    public void setEditorList(List<String> editorList) {
        this.editorList = editorList;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getNewsOutlet() {
        return newsOutlet;
    }

    public void setNewsOutlet(String newsOutlet) {
        this.newsOutlet = newsOutlet;
    }

}
