package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

public class Filter extends Mapping {

    public final String value;

    public Filter(String field, String value) {
        super(field);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public static Filter of(Object input) {
        Map<String, Object> filter = (Map<String, Object>) input;
        String field = (String) filter.get("field");
        String value = (String) filter.get("value");
        return new Filter(field, value);
    }

    public static Filter of(String field, String value) {
        return new Filter(field, value);
    }

}
