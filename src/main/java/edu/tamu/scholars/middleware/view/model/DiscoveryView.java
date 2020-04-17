package edu.tamu.scholars.middleware.view.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "discovery_views")
public class DiscoveryView extends CollectionView {

    private static final long serialVersionUID = 6627502439871091387L;

    @Column(nullable = true)
    private String defaultSearchField;

    @ElementCollection
    private List<String> highlightFields;

    @Column(nullable = true)
    private String highlightPrefix;

    @Column(nullable = true)
    private String highlightPostfix;

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

    public String getHighlightPrefix() {
        return highlightPrefix;
    }

    public void setHighlightPre(String highlightPrefix) {
        this.highlightPrefix = highlightPrefix;
    }

    public String getHighlightPostfix() {
        return highlightPostfix;
    }

    public void setHighlightPost(String highlightPostfix) {
        this.highlightPostfix = highlightPostfix;
    }

}
