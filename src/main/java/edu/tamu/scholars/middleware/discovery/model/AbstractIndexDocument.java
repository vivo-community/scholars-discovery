package edu.tamu.scholars.middleware.discovery.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.tamu.scholars.middleware.discovery.annotation.FieldSource;
import edu.tamu.scholars.middleware.discovery.annotation.FieldType;

public abstract class AbstractIndexDocument {

    @Field
    @FieldType(required = true, readonly = true)
    private String id;

    @JsonProperty("class")
    @Field("class")
    @FieldType(type = "string", value = "class", required = true)
    private String clazz = this.getClass().getSimpleName();

    @Field
    @FieldType(type = "whole_strings")
    @FieldSource(template = "common/type", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> type;

    @Field
    @FieldType(type = "strings")
    private List<String> syncIds = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getSyncIds() {
        return syncIds;
    }

    public void setSyncIds(List<String> syncIds) {
        this.syncIds = syncIds;
    }

}
