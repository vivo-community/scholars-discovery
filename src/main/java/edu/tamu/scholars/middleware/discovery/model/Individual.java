package edu.tamu.scholars.middleware.discovery.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.leangen.graphql.annotations.GraphQLIgnore;

@GraphQLIgnore
@JsonInclude(NON_EMPTY)
@SolrDocument(collection = "scholars-discovery")
public class Individual extends Common {

    @Dynamic
    @Field("*")
    @Indexed(readonly = true, stored = false, searchable = false)
    private Map<String, List<String>> content;

    public Individual() {

    }

    public Map<String, List<String>> getContent() {
        return content;
    }

    public void setContent(Map<String, List<String>> content) {
        this.content = content;
    }

}
