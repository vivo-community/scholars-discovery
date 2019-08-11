package edu.tamu.scholars.middleware.discovery.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

import io.leangen.graphql.annotations.types.GraphQLInterface;

@GraphQLInterface(name = "AbstractSolrDocument", implementationAutoDiscovery = true)
public abstract class AbstractSolrDocument {

    @Id
    @Indexed
    private String id;

    @Indexed
    private Set<String> syncIds = new HashSet<String>();

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
