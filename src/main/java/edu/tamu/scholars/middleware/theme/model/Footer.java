package edu.tamu.scholars.middleware.theme.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

@Embeddable
public class Footer {

    @ElementCollection
    @CollectionTable(name = "theme_footer_links")
    private List<Link> links;

    @ElementCollection
    @CollectionTable(name = "theme_footer_variables")
    private List<Style> variables;

    @Column
    private String copyright;

    public Footer() {
        super();
        this.links = new ArrayList<Link>();
        this.variables = new ArrayList<Style>();
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Style> getVariables() {
        return variables;
    }

    public void setVariables(List<Style> variables) {
        this.variables = variables;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
