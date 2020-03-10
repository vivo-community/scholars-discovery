package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

public class HighlightArg extends MappingArg {

    // NOTE: this is wrong
    private final float value;

    public HighlightArg(String field, float value) {
        super(field);
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}