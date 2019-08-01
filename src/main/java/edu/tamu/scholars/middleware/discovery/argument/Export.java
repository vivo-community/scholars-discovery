package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

public class Export extends Mapping {

    private final String label;

    public Export(String field, String label) {
        super(field);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @SuppressWarnings("unchecked")
    public static Export of(Object input) {
        Map<String, Object> export = (Map<String, Object>) input;
        String field = (String) export.get("field");
        String label = (String) export.get("label");
        return new Export(field, label);
    }

    public static Export of(String parameter) {
        String[] parts = parameter.split(",");
        return new Export(parts[0], parts.length > 1 ? parts[1] : parts[0]);
    }

}
