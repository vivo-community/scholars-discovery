package edu.tamu.scholars.middleware.view.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Boost {

    @Column(nullable = false)
    private String field;

    @Column(nullable = false)
    private float value;

    public Boost() {

    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
