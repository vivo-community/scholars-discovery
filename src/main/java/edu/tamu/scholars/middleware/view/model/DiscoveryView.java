package edu.tamu.scholars.middleware.view.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(NON_EMPTY)
@Table(name = "discovery_views")
public class DiscoveryView extends CollectionView {

    private static final long serialVersionUID = 6627502439871091387L;

    @Column(nullable = true)
    private String defaultSearchField;

    @ElementCollection
    private List<String> highlightFields;

    @Column(nullable = true)
    private String highlightPre;

    @Column(nullable = true)
    private String highlightPost;

    public DiscoveryView() {
        super();
        this.highlightFields = new ArrayList<>();
    }

    public String getDefaultSearchField() {
        return defaultSearchField;
    }

    public void setDefaultSearchField(String defaultSearchField) {
        this.defaultSearchField = defaultSearchField;
    }

    public List<String> getHighlightFields() {
        return highlightFields;
    }

    public void setHighlightFields(List<String> highlightFields) {
        this.highlightFields = highlightFields;
    }

    public String getHighlightPre() {
        return highlightPre;
    }

    public void setHighlightPre(String highlightPre) {
        this.highlightPre = highlightPre;
    }

    public String getHighlightPost() {
        return highlightPost;
    }

    public void setHighlightPost(String highlightPost) {
        this.highlightPost = highlightPost;
    }

}
