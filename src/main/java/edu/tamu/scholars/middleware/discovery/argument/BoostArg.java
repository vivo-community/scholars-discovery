package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

import edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility;

public class BoostArg {

    private final String field;

    private final float value;

    BoostArg(String field, float value) {
        this.field = DiscoveryUtility.findProperty(field);
        this.value = value;
    }

    public String getField() {
        return field;
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
