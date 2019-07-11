package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

public class ExportArg extends MappingArg {

    private final String label;

    private final String delimiter;

    public ExportArg(String field, String label, String delimiter) {
        super(field);
        this.label = label;
        this.delimiter = delimiter;
    }

    public String getLabel() {
        return label;
    }

    public String getDelimiter() {
        return delimiter;
    }

    @SuppressWarnings("unchecked")
    public static ExportArg of(Object input) {
        Map<String, Object> export = (Map<String, Object>) input;
        String field = (String) export.get("field");
        String label = (String) export.get("label");
        String delimiter = (String) export.get("delimiter");
        return new ExportArg(field, label, delimiter);
    }

    public static ExportArg of(String parameter) {
        String[] parts = parameter.split(",");
        return new ExportArg(parts[0], parts.length > 1 ? parts[1] : parts[0], parts.length > 2 ? parts[2] : "||");
    }

}
