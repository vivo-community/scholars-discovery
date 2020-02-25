package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import edu.tamu.scholars.middleware.model.OpKey;

public class FilterArg extends MappingArg {

    private final static String EMPTY_STRING = "";

    private final String value;

    private final OpKey opKey;

    private final String field;
    private final String tag;

    public FilterArg(String field, String value, OpKey opKey, String tag) {
        super(field);
        // redudant just for easier name recognition
        this.field = field;
        this.value = value;
        this.opKey = opKey;
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public OpKey getOpKey() {
        return opKey;
    }

    public String getTag() {
        return tag;
    }

    public String getField() {
        return field;
    }

    public String getCommand() {
        if (tag != "") {
            return "{!tag=" + tag + "}" + field;
        } else {
            return field;
        }
    }

    @SuppressWarnings("unchecked")
    public static FilterArg of(Object input) {
        Map<String, Object> filter = (Map<String, Object>) input;
        String field = (String) filter.get("field");
        String value = (String) filter.get("value");
        String opKey = (String) filter.get("opKey");
        String tag = (String) filter.get("tag");
        return new FilterArg(field, value, OpKey.valueOf(opKey), tag);
    }

    public static FilterArg of(String field, Optional<String> value, 
      Optional<String> opKey, Optional<String> tag) {
        String valueParam = value.isPresent() ? value.get() : EMPTY_STRING;
        OpKey opKeyParam = opKey.isPresent() ? OpKey.valueOf(opKey.get()) : OpKey.EQUALS;
        String tagParam = tag.isPresent() ? tag.get() : EMPTY_STRING;
        return new FilterArg(field, valueParam, opKeyParam, tagParam);
    }

}
