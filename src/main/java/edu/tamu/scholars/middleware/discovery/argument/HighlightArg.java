package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class HighlightArg {

    private final String[] fields;

    private final String prefix;

    private final String postfix;

    public HighlightArg(String[] fields, String prefix, String postfix) {
        this.fields = fields;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String[] getFields() {
        return fields;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    @SuppressWarnings("unchecked")
    public static HighlightArg of(Object input) {
        Map<String, Object> highlight = (Map<String, Object>) input;
        String[] fields = (String[]) highlight.get("fields");
        String prefix = highlight.containsKey("prefix") ? (String) highlight.get("prefix") : StringUtils.EMPTY;
        String postfix = highlight.containsKey("postfix") ? (String) highlight.get("postfix") : StringUtils.EMPTY;
        return new HighlightArg(fields, prefix, postfix);
    }

    public static HighlightArg of(String[] fields, Optional<String> prefix, Optional<String> postfix) {
        String prefixParam = prefix.isPresent() ? prefix.get() : StringUtils.EMPTY;
        String postfixParam = postfix.isPresent() ? postfix.get() : StringUtils.EMPTY;
        return new HighlightArg(fields, prefixParam, postfixParam);
    }

}
