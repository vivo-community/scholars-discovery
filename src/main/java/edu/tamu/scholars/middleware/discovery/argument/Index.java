package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

import org.springframework.data.solr.core.query.Criteria.OperationKey;

public class Index extends Mapping {

    private final OperationKey operationKey;

    private final String option;

    public Index(String field, OperationKey operationKey, String option) {
        super(field);
        this.operationKey = operationKey;
        this.option = option;
    }

    public OperationKey getOperationKey() {
        return operationKey;
    }

    public String getOption() {
        return option;
    }

    @SuppressWarnings("unchecked")
    public static Index of(Object input) {
        Map<String, Object> index = (Map<String, Object>) input;
        String field = (String) index.get("field");
        OperationKey operationKey = (OperationKey) index.get("operationKey");
        String option = (String) index.get("option");
        return new Index(field, operationKey, option);
    }

    public static Index of(String index) {
        String[] parts = index.split(",");
        return new Index(parts[0], parts.length > 1 ? OperationKey.valueOf(parts[1]) : OperationKey.STARTS_WITH, parts.length > 2 ? parts[2] : "A");
    }

}
