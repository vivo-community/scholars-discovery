package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class HighlightArg {

    private final String fields;

    private final String pre;

    private final String post;

    public HighlightArg(String fields, String pre, String post) {
        this.fields = fields;
        this.pre = pre;
        this.post = post;
    }

    public String getFields() {
        return fields;
    }

    public String getPre() {
        return pre;
    }

    public String getPost() {
        return post;
    }

    @SuppressWarnings("unchecked")
    public static HighlightArg of(Object input) {
        Map<String, Object> highlight = (Map<String, Object>) input;
        String fields = (String) highlight.get("fields");
        String pre = highlight.containsKey("pre") ? (String) highlight.get("pre") : StringUtils.EMPTY;
        String post = highlight.containsKey("post") ? (String) highlight.get("post") : StringUtils.EMPTY;
        return new HighlightArg(fields, pre, post);
    }

    public static HighlightArg of(String fields, Optional<String> pre, Optional<String> post) {
        String preParam = pre.isPresent() ? pre.get() : StringUtils.EMPTY;
        String postParam = post.isPresent() ? post.get() : StringUtils.EMPTY;
        return new HighlightArg(fields, preParam, postParam);
    }

}
