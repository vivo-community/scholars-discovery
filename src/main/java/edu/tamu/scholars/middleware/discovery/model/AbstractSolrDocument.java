package edu.tamu.scholars.middleware.discovery.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.leangen.graphql.annotations.types.GraphQLInterface;

@GraphQLInterface(name = "AbstractSolrDocument", implementationAutoDiscovery = true)
public abstract class AbstractSolrDocument {

    @Id
    @Indexed
    private String id;

    @Field("class")
    @JsonProperty("class")
    @Indexed(type = "whole_string", value = "class")
    private String clazz = this.getClass().getSimpleName();

    @Indexed
    private Set<String> syncIds = new HashSet<String>();

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

    public Set<String> getSyncIds() {
        return syncIds;
    }

    public void setSyncIds(Set<String> syncIds) {
        this.syncIds = syncIds;
    }

}
