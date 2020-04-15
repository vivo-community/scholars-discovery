package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import io.leangen.graphql.annotations.GraphQLIgnore;

@GraphQLIgnore
@JsonInclude(NON_EMPTY)
@SolrDocument(collection = "scholars-discovery")
@CollectionSource(name = "processes", predicate = "http://purl.obolibrary.org/obo/BFO_0000015")
public class Process extends Common {

    @Indexed(type = "whole_string", copyTo = "_text_")
    @PropertySource(template = "process/title", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private String title;

    @Indexed(type = "tokenized_string")
    @PropertySource(template = "process/description", predicate = "http://vivoweb.org/ontology/core#description")
    private String description;

    @NestedObject
    @Indexed(type = "nested_whole_strings")
    @PropertySource(template = "process/offeredBy", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> offeredBy;

    @Indexed(type = "pdate")
    @PropertySource(template = "process/dateTimeIntervalStart", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateTimeIntervalStart;

    @Indexed(type = "pdate")
    @PropertySource(template = "process/dateTimeIntervalEnd", predicate = "http://vivoweb.org/ontology/core#dateTime")
    private String dateTimeIntervalEnd;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/occursWithinEvent", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> occursWithinEvent;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/includesEvent", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> includesEvent;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/inEventSeries", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> inEventSeries;

    @Indexed(type = "nested_whole_strings", copyTo = "_text_")
    @NestedObject(properties = { @Reference(value = "participantRole", key = "role") })
    @PropertySource(template = "process/participant", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> participants;

    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/participantRole", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> participantRole;

    @NestedObject
    @Indexed(type = "nested_whole_strings")
    @PropertySource(template = "process/hasSubjectArea", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> subjectAreas;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/hasPrerequisite", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> hasPrerequisite;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/prerequisiteFor", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> prerequisiteFor;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "process/credits", predicate = "http://vivoweb.org/ontology/core#courseCredits")
    private String credits;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/outputPublicationOrOtherWork", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> outputPublicationOrOtherWork;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/relatedDocument", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> relatedDocuments;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "process/contactInformation", predicate = "http://vivoweb.org/ontology/core#contactInformation")
    private String contactInformation;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/heldInFacility", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> heldInFacility;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/heldInGeographicLocation", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> heldInGeographicLocation;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/hasOutput", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> hasOutput;

    @NestedObject
    @Indexed(type = "nested_whole_strings", searchable = false)
    @PropertySource(template = "process/hasParticipant", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> hasParticipant;

    public Process() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOfferedBy() {
        return offeredBy;
    }

    public void setOfferedBy(List<String> offeredBy) {
        this.offeredBy = offeredBy;
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

    public List<String> getOccursWithinEvent() {
        return occursWithinEvent;
    }

    public void setOccursWithinEvent(List<String> occursWithinEvent) {
        this.occursWithinEvent = occursWithinEvent;
    }

    public List<String> getIncludesEvent() {
        return includesEvent;
    }

    public void setIncludesEvent(List<String> includesEvent) {
        this.includesEvent = includesEvent;
    }

    public List<String> getInEventSeries() {
        return inEventSeries;
    }

    public void setInEventSeries(List<String> inEventSeries) {
        this.inEventSeries = inEventSeries;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public List<String> getParticipantRole() {
        return participantRole;
    }

    public void setParticipantRole(List<String> participantRole) {
        this.participantRole = participantRole;
    }

    public List<String> getSubjectAreas() {
        return subjectAreas;
    }

    public void setSubjectAreas(List<String> subjectAreas) {
        this.subjectAreas = subjectAreas;
    }

    public List<String> getHasPrerequisite() {
        return hasPrerequisite;
    }

    public void setHasPrerequisite(List<String> hasPrerequisite) {
        this.hasPrerequisite = hasPrerequisite;
    }

    public List<String> getPrerequisiteFor() {
        return prerequisiteFor;
    }

    public void setPrerequisiteFor(List<String> prerequisiteFor) {
        this.prerequisiteFor = prerequisiteFor;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public List<String> getOutputPublicationOrOtherWork() {
        return outputPublicationOrOtherWork;
    }

    public void setOutputPublicationOrOtherWork(List<String> outputPublicationOrOtherWork) {
        this.outputPublicationOrOtherWork = outputPublicationOrOtherWork;
    }

    public List<String> getRelatedDocuments() {
        return relatedDocuments;
    }

    public void setRelatedDocuments(List<String> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public List<String> getHeldInFacility() {
        return heldInFacility;
    }

    public void setHeldInFacility(List<String> heldInFacility) {
        this.heldInFacility = heldInFacility;
    }

    public List<String> getHeldInGeographicLocation() {
        return heldInGeographicLocation;
    }

    public void setHeldInGeographicLocation(List<String> heldInGeographicLocation) {
        this.heldInGeographicLocation = heldInGeographicLocation;
    }

    public List<String> getHasOutput() {
        return hasOutput;
    }

    public void setHasOutput(List<String> hasOutput) {
        this.hasOutput = hasOutput;
    }

    public List<String> getHasParticipant() {
        return hasParticipant;
    }

    public void setHasParticipant(List<String> hasParticipant) {
        this.hasParticipant = hasParticipant;
    }

}
