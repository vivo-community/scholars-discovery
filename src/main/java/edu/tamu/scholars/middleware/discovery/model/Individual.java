package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.Collection;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(NON_EMPTY)
@Relation(collectionRelation = "individual", itemRelation = "individual")
public class Individual extends AbstractIndexDocument {

    @Field("*")
    private Map<String, Collection<Object>> content;

    public Individual() {

    }

    public Map<String, Collection<Object>> getContent() {
        return content;
    }

    public void setContent(Map<String, Collection<Object>> content) {
        this.content = content;
    }

}
