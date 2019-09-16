package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import edu.tamu.scholars.middleware.model.OpKey;

public class FilterArg extends MappingArg {

    private final static String EMPTY_STRING = "";

    private final String value;

    private final OpKey opKey;

    public FilterArg(String field, String value, OpKey opKey) {
        super(field);
        this.value = value;
        this.opKey = opKey;
    }

    public String getValue() {
        return value;
    }

    public OpKey getOpKey() {
        return opKey;
    }

    @SuppressWarnings("unchecked")
    public static FilterArg of(Object input) {
        Map<String, Object> filter = (Map<String, Object>) input;
        String field = (String) filter.get("field");
        String value = (String) filter.get("value");
        String opKey = (String) filter.get("opKey");
        return new FilterArg(field, value, OpKey.valueOf(opKey));
    }

    public static FilterArg of(String field, Optional<String> value, Optional<String> opKey) {
        return new FilterArg(field, value.isPresent() ? value.get() : EMPTY_STRING, opKey.isPresent() ? OpKey.valueOf(opKey.get()) : OpKey.EQUALS);
    }

}
