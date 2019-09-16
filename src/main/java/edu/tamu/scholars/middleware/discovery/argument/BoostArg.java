package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

public class BoostArg extends MappingArg {

    private final float value;

    public BoostArg(String field, float value) {
        super(field);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public static BoostArg of(Object input) {
        Map<String, Object> facet = (Map<String, Object>) input;
        String field = (String) facet.get("field");
        float value = (float) facet.get("value");
        return new BoostArg(field, value);
    }

    public static BoostArg of(String parameter) {
        String[] parts = parameter.split(",");
        return new BoostArg(parts[0], parts.length > 1 ? Float.valueOf(parts[1]) : 1.0f);
    }

}
