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
@CollectionSource(name = "documents", predicate = "http://purl.org/ontology/bibo/Document")
public class Document extends Common {

    @Indexed(type = "sorting_string", copyTo = "_text_")
    @PropertySource(template = "document/title", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String title;

    @Field("abstract")
    @JsonProperty("abstract")
    @Indexed(type = "whole_string", value = "abstract", copyTo = "_text_")
    @PropertySource(template = "document/abstract", predicate = "http://purl.org/ontology/bibo/abstract")
    private String abstractText;

    @Indexed(type = "whole_string", copyTo = "_text_")
    @PropertySource(template = "document/abbreviation", predicate = "http://vivoweb.org/ontology/core#abbreviation")
    private String abbreviation;

    @NestedObject
    @Indexed(type = "nested_string", copyTo = "_text_")
    @PropertySource(template = "document/publicationVenue", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private String publicationVenue;

    @NestedObject
    @Indexed(type = "nested_string", searchable = false)
    @PropertySource(template = "document/hasPublicationVenueFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private String hasPublicationVenueFor;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "authorOrganization", key = "organizations") })
    @PropertySource(template = "document/author", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> authors;

    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @Indexed(type = "nested_strings")
    @PropertySource(template = "document/authorOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> authorOrganization;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/editor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> editors;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/translator", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> translators;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/status", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String status;

    @Indexed(type = "pdate")
    @PropertySource(template = "document/publicationDate", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String publicationDate;

    @NestedObject
    @Indexed(type = "nested_string")
    @PropertySource(template = "document/publisher", predicate = "http://www.w3.org/2000/01/rdf-schema#label", unique = true)
    private String publisher;

    @Indexed(type = "pdate", searchable = false)
    @PropertySource(template = "document/dateFiled", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateFiled;

    @Indexed(type = "pdate", searchable = false)
    @PropertySource(template = "document/dateIssued", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateIssued;

    @NestedObject
    @Indexed(type = "nested_strings")
    @PropertySource(template = "document/hasSubjectArea", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> subjectAreas;

    @Indexed(type = "whole_strings", searchable = false)
    @PropertySource(template = "document/hasRestriction", predicate = "http://purl.obolibrary.org/obo/ERO_0000045")
    private List<String> restrictions;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/documentPart", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> documentParts;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/chapter", predicate = "http://purl.org/ontology/bibo/chapter")
    private String chapter;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/feature", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> features;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/edition", predicate = "http://purl.org/ontology/bibo/edition")
    private String edition;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/documentationForProjectOrResource", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> documentationForProjectOrResource;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/outputOfProcessOrEvent", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> outputOfProcessOrEvent;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/presentedAt", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> presentedAt;

    @Indexed(type = "whole_strings", copyTo = "_text_")
    @PropertySource(template = "document/keyword", predicate = "http://vivoweb.org/ontology/core#freetextKeyword")
    private List<String> keywords;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/eanucc13", predicate = "http://purl.org/ontology/bibo/eanucc13")
    private String eanucc13;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/nihmsid", predicate = "http://vivoweb.org/ontology/core#nihmsid")
    private String nihmsid;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/pmcid", predicate = "http://vivoweb.org/ontology/core#pmcid")
    private String pmcid;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/identifier", predicate = "http://purl.org/ontology/bibo/identifier")
    private String identifier;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/patentNumber", predicate = "http://vivoweb.org/ontology/core#patentNumber")
    private String patentNumber;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/doi", predicate = "http://purl.org/ontology/bibo/doi")
    private String doi;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/oclcnum", predicate = "http://purl.org/ontology/bibo/oclcnum")
    private String oclcnum;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/isbn10", predicate = "http://purl.org/ontology/bibo/isbn10")
    private String isbn10;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/isbn13", predicate = "http://purl.org/ontology/bibo/isbn13")
    private String isbn13;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/pmid", predicate = "http://purl.org/ontology/bibo/pmid")
    private String pmid;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/lccn", predicate = "http://purl.org/ontology/bibo/lccn")
    private String lccn;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/issn", predicate = "http://purl.org/ontology/bibo/issn")
    private String issn;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/eissn", predicate = "http://purl.org/ontology/bibo/eissn")
    private String eissn;

    @Indexed(type = "whole_strings")
    @PropertySource(template = "document/uri", predicate = "http://purl.org/ontology/bibo/uri")
    private String uri;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/citedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> citedBy;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/cites", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> cites;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/citesAsDataSource", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> citesAsDataSource;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/hasTranslation", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> translations;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/translationOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> translationOf;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/globalCitationFrequency", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> globalCitationFrequency;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/iclCode", predicate = "http://vivoweb.org/ontology/core#iclCode")
    private String iclCode;

    @Indexed(type = "pint")
    @PropertySource(template = "document/numberOfPages", predicate = "http://purl.org/ontology/bibo/numPages")
    private Integer numberOfPages;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/pageStart", predicate = "http://purl.org/ontology/bibo/pageStart")
    private String pageStart;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/pageEnd", predicate = "http://purl.org/ontology/bibo/pageEnd")
    private String pageEnd;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/volume", predicate = "http://purl.org/ontology/bibo/volume")
    private String volume;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/issue", predicate = "http://purl.org/ontology/bibo/issue")
    private String issue;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/placeOfPublication", predicate = "http://vivoweb.org/ontology/core#placeOfPublication")
    private String placeOfPublication;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/assignee", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> assignees;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/reproducedIn", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> reproducedIn;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/reproduces", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> reproduces;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/isAbout", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> isAbout;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/specifiedOutputOf", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> specifiedOutputOf;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "document/isTemplate", predicate = "http://purl.obolibrary.org/obo/ARG_0000001")
    private String isTemplate;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/mention", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> mentions;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/participatesIn", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> participatesIn;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/supportedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> supportedBy;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "document/receipt", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> receipts;

    @Indexed(type = "pfloat")
    @PropertySource(template = "document/altmetricScore", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#AltmetricScore")
    private Float altmetricScore;

    @Indexed(type = "pint")
    @PropertySource(template = "document/citationCount", predicate = "http://vivo.library.tamu.edu/ontology/TAMU#CitationCount")
    private Integer citationCount;

    @Indexed(type = "whole_strings")
    @PropertySource(template = "document/tag", predicate = "http://purl.obolibrary.org/obo/ARG_0000015")
    private List<String> tags;

    @Indexed(type = "whole_string")
    @PropertySource(template = "document/note", predicate = "http://www.w3.org/2006/vcard/ns#note")
    private String note;

    @Indexed(type = "nested_strings")
    @NestedObject(properties = { @Reference(value = "advisedByEmail", key = "email"), @Reference(value = "advisedByOrganization", key = "organization") })
    @PropertySource(template = "document/advisedBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> advisedBy;

    @Indexed(type = "nested_strings")
    @PropertySource(template = "document/advisedByEmail", predicate = "http://www.w3.org/2006/vcard/ns#email")
    private List<String> advisedByEmail;

    @NestedMultiValuedProperty
    @NestedObject(root = false)
    @Indexed(type = "nested_strings")
    @PropertySource(template = "document/advisedByOrganization", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> advisedByOrganization;

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

    public List<String> getAdvisedBy() {
        return advisedBy;
    }

    public void setAdvisedBy(List<String> advisedBy) {
        this.advisedBy = advisedBy;
    }

    public List<String> getAdvisedByEmail() {
        return advisedByEmail;
    }

    public void setAdvisedByEmail(List<String> advisedByEmail) {
        this.advisedByEmail = advisedByEmail;
    }

    public List<String> getAdvisedByOrganization() {
        return advisedByOrganization;
    }

    public void setAdvisedByOrganization(List<String> advisedByOrganization) {
        this.advisedByOrganization = advisedByOrganization;
    }

}
