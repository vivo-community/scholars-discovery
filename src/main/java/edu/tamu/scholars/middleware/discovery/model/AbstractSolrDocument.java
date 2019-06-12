package edu.tamu.scholars.middleware.discovery.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

public abstract class AbstractSolrDocument {

    @Id
    @Indexed
    private String id;

    @Indexed
    private Set<String> syncIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getSyncIds() {
        return syncIds;
    }

    public void setSyncIds(Set<String> syncIds) {
        this.syncIds = syncIds;
    }

}
