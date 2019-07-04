package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

import org.springframework.data.solr.core.query.FacetOptions.FacetSort;

public class Facet extends Mapping {

    public final FacetSort sort;

    public final int limit;

    public final int offset;

    public Facet(String field, FacetSort sort, int limit, int offset) {
        super(field);
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
    }

    public FacetSort getSort() {
        return sort;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    @SuppressWarnings("unchecked")
    public static Facet of(Object input) {
        Map<String, Object> facet = (Map<String, Object>) input;
        String field = (String) facet.get("field");
        FacetSort sort = (FacetSort) facet.get("sort");
        Integer limit = (Integer) facet.get("limit");
        Integer offset = (Integer) facet.get("offset");
        return new Facet(field, sort, limit, offset);
    }

    public static Facet of(String field) {
        return new Facet(field, FacetSort.COUNT, 10, 0);
    }

}
