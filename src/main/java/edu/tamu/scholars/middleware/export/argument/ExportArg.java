package edu.tamu.scholars.middleware.export.argument;

import java.util.Map;

import edu.tamu.scholars.middleware.discovery.argument.MappingArg;

public class ExportArg extends MappingArg {

    private final String label;

    public ExportArg(String field, String label) {
        super(field);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @SuppressWarnings("unchecked")
    public static ExportArg of(Object input) {
        Map<String, Object> export = (Map<String, Object>) input;
        String field = (String) export.get("field");
        String label = (String) export.get("label");
        return new ExportArg(field, label);
    }

    public static ExportArg of(String parameter) {
        String[] parts = parameter.split(",");
        return new ExportArg(parts[0], parts.length > 1 ? parts[1] : parts[0]);
    }

}
