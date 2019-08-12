package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

import org.springframework.data.solr.core.query.Criteria.OperationKey;

public class IndexArg extends MappingArg {

    private final OperationKey operationKey;

    private final String option;

    public IndexArg(String field, OperationKey operationKey, String option) {
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
    public static IndexArg of(Object input) {
        Map<String, Object> index = (Map<String, Object>) input;
        String field = (String) index.get("field");
        OperationKey operationKey = (OperationKey) index.get("operationKey");
        String option = (String) index.get("option");
        return new IndexArg(field, operationKey, option);
    }

    public static IndexArg of(String index) {
        String[] parts = index.split(",");
        return new IndexArg(parts[0], parts.length > 1 ? OperationKey.valueOf(parts[1]) : OperationKey.STARTS_WITH, parts.length > 2 ? parts[2] : "A");
    }

}
