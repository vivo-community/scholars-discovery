package edu.tamu.scholars.middleware.graphql.config.model;

import java.util.ArrayList;
import java.util.List;

public class Composite {

    private String type;

    private List<CompositeReference> references;

    public Composite() {
        this.references = new ArrayList<CompositeReference>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CompositeReference> getReferences() {
        return references;
    }

    public void setReferences(List<CompositeReference> references) {
        this.references = references;
    }

}
