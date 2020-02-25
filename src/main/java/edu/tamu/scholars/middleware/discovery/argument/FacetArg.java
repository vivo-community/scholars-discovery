package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import edu.tamu.scholars.middleware.view.model.FacetType;
import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findProperty;

public class FacetArg extends MappingArg {

    private final static String EMPTY_STRING = "";
    
    private final FacetSortArg sort;

    private final int pageSize;

    private final int pageNumber;

    private final FacetType type;

    private final String field;

    private final String exclusionTag;

    // maybe use 'tag' field instead
    public FacetArg(String field, String sort, int pageSize, int pageNumber, 
      String type, String exclusionTag) {
        // e.g. !{ex=lc}locality -> locality, SOLR gets 'command'
        // but maps back to 'field' name
        super(field);
        //String fieldWithoutTag = field.replaceAll("\\{\\!.*\\}", "");
        //this.command = field;
        this.field = field;
        this.sort = FacetSortArg.of(sort);
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.exclusionTag = exclusionTag;
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

    public String getField() {
        return field;
    }

    public String getCommand() {
        if (exclusionTag != "") {
            return "{!ex=" + exclusionTag + "}" + field;
        } else {
            return field;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static FacetArg of(Object input) {
        Map<String, Object> facet = (Map<String, Object>) input;
        String field = (String) facet.get("field");
        String sort = (String) facet.get("sort");
        int pageSize = (int) facet.get("pageSize");
        int pageNumber = (int) facet.get("pageNumber");
        String type = (String) facet.get("type");
        String exclusionTag = (String) facet.get("exclusionTag");
        return new FacetArg(field, sort, pageSize, pageNumber, type, exclusionTag);
    }

    public static FacetArg of(String field, Optional<String> sort, 
      Optional<String> pageSize, Optional<String> pageNumber, 
      Optional<String> type, Optional<String> exclusionTag) {
        // @formatter:off
        return new FacetArg(field,
            sort.isPresent() ? sort.get() : "COUNT,DESC",
            pageSize.isPresent() ? Integer.valueOf(pageSize.get()) : 10,
            pageNumber.isPresent() ? Integer.valueOf(pageNumber.get()) : 1,
            type.isPresent() ? type.get() : "STRING",
            exclusionTag.isPresent() ? exclusionTag.get() : EMPTY_STRING);
        // @formatter:on
    }

}
