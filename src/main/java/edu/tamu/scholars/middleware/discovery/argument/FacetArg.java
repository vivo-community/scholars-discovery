package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import edu.tamu.scholars.middleware.view.model.FacetType;

public class FacetArg extends MappingArg {

    private final FacetSortArg sort;

    private final int pageSize;

    private final int pageNumber;

    private final FacetType type;

    public FacetArg(String field, String sort, int pageSize, int pageNumber, String type) {
        super(field);
        this.sort = FacetSortArg.of(sort);
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.type = FacetType.valueOf(type);
    }

    public FacetSortArg getSort() {
        return sort;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public FacetType getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public static FacetArg of(Object input) {
        Map<String, Object> facet = (Map<String, Object>) input;
        String field = (String) facet.get("field");
        String sort = (String) facet.get("sort");
        Integer pageSize = (Integer) facet.get("pageSize");
        Integer pageNumber = (Integer) facet.get("pageNumber");
        String type = (String) facet.get("type");
        return new FacetArg(field, sort, pageSize, pageNumber, type);
    }

    public static FacetArg of(String field, Optional<String> sort, Optional<String> pageSize, Optional<String> pageNumber, Optional<String> type) {
        // @formatter:off
        return new FacetArg(field,
            sort.isPresent() ? sort.get() : "COUNT,DESC",
            pageSize.isPresent() ? Integer.valueOf(pageSize.get()) : 10,
            pageNumber.isPresent() ? Integer.valueOf(pageNumber.get()) : 0,
            type.isPresent() ? type.get() : "STRING");
        // @formatter:on
    }

}
