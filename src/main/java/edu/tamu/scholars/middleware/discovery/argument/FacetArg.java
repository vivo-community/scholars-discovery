package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import edu.tamu.scholars.middleware.view.model.FacetType;

public class FacetArg extends MappingArg {

    private final FacetSortArg sort;

    private final int pageSize;

    private final int pageNumber;

    private final FacetType type;

    private final String field;

    private final String exclusionTag;

    public FacetArg(String field, String sort, int pageSize, int pageNumber, String type, String exclusionTag) {
        super(field);
        this.field = field;
        this.sort = FacetSortArg.of(sort);
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.type = FacetType.valueOf(type);
        this.exclusionTag = exclusionTag;
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

    public String getField() {
        return field;
    }

    public String getCommand() {
        String property = getProperty();
        return StringUtils.isEmpty(exclusionTag) ? property : String.format("{!ex=%s}%s", exclusionTag, property);
    }

    @SuppressWarnings("unchecked")
    public static FacetArg of(Object input) {
        Map<String, Object> facet = (Map<String, Object>) input;
        String field = (String) facet.get("field");
        String sort = (String) facet.get("sort");
        int pageSize = (int) facet.get("pageSize");
        int pageNumber = (int) facet.get("pageNumber");
        String type = (String) facet.get("type");
        String exclusionTag = facet.containsKey("exclusionTag") ? (String) facet.get("exclusionTag") : StringUtils.EMPTY;
        return new FacetArg(field, sort, pageSize, pageNumber, type, exclusionTag);
    }

    public static FacetArg of(String field, Optional<String> sort, Optional<String> pageSize, Optional<String> pageNumber, Optional<String> type, Optional<String> exclusionTag) {
        String sortParam = sort.isPresent() ? sort.get() : "COUNT,DESC";
        int pageSizeParam = pageSize.isPresent() ? Integer.valueOf(pageSize.get()) : 10;
        int pageNumberParam = pageNumber.isPresent() ? Integer.valueOf(pageNumber.get()) : 1;
        String typeParam = type.isPresent() ? type.get() : "STRING";
        String exclusionTagParam = exclusionTag.isPresent() ? exclusionTag.get() : StringUtils.EMPTY;
        return new FacetArg(field, sortParam, pageSizeParam, pageNumberParam, typeParam, exclusionTagParam);
    }

}
