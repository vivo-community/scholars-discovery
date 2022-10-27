package edu.tamu.scholars.middleware.discovery.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;

public abstract class AbstractIndexDocument {

    @Id
    @Indexed(required = true)
    private String id;

    @Indexed(type = "whole_strings")
    @PropertySource(template = "common/type", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> type;

    @Field("class")
    @JsonProperty("class")
    @Indexed(type = "string", value = "class", required = true)
    private String clazz = this.getClass().getSimpleName();

    @Indexed
    private Set<String> syncIds = new HashSet<String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Set<String> getSyncIds() {
        return syncIds;
    }

    public void setSyncIds(Set<String> syncIds) {
        this.syncIds = syncIds;
    }

}
