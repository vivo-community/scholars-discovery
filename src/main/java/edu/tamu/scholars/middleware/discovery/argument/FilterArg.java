package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

public class FilterArg extends MappingArg {

    private final String value;

    public FilterArg(String field, String value) {
        super(field);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public static FilterArg of(Object input) {
        Map<String, Object> filter = (Map<String, Object>) input;
        String field = (String) filter.get("field");
        String value = (String) filter.get("value");
        return new FilterArg(field, value);
    }

    public static FilterArg of(String field, String value) {
        return new FilterArg(field, value);
    }

}
