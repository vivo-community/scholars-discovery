package edu.tamu.scholars.middleware.discovery.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;

public class ArgumentUtility {

    private final static String FACET_QUERY_PARAM_KEY = "facets";
    private final static String FILTER_QUERY_PARAM_KEY = "filters";
    private final static String BOOST_QUERY_PARAM_KEY = "boost";

    private final static String QUERY_EXPRESSION_QUERY_PARAM_KEY = "q";
    private final static String DEFAULT_FIELD_QUERY_PARAM_KEY = "df";
    private final static String MINIMUM_SHOULD_MATCH_QUERY_PARAM_KEY = "mm";
    private final static String QUERY_FIELD_QUERY_PARAM_KEY = "qf";
    private final static String BOOST_QUERY_QUERY_PARAM_KEY = "bq";
    private final static String FIELDS_QUERY_PARAM_KEY = "fl";

    private final static String HIGHLIGHT_FIELDS_QUERY_PARAM_KEY = "hl";
    private final static String HIGHLIGHT_PRE_QUERY_PARAM_KEY = "hl.prefix";
    private final static String HIGHLIGHT_POST_QUERY_PARAM_KEY = "hl.postfix";

    private final static String FACET_SORT_FORMAT = "%s.sort";
    private final static String FACET_PAGE_SIZE_FORMAT = "%s.pageSize";
    private final static String FACET_PAGE_NUMBER_FORMAT = "%s.pageNumber";
    private final static String FACET_TYPE_FORMAT = "%s.type";
    private final static String FACET_EXCLUDE_TAG_FORMAT = "%s.exclusionTag";
    private final static String FACET_RANGE_START_TAG_FORMAT = "%s.rangeStart";
    private final static String FACET_RANGE_END_TAG_FORMAT = "%s.rangeEnd";
    private final static String FACET_RANGE_GAP_TAG_FORMAT = "%s.rangeGap";

    private final static String FILTER_VALUE_FORMAT = "%s.filter";
    private final static String FILTER_OPKEY_FORMAT = "%s.opKey";
    private final static String FILTER_TAG_FORMAT = "%s.tag";

    public static List<FacetArg> getFacetArguments(HttpServletRequest request) {
        List<String> perameterNames = Collections.list(request.getParameterNames());
        // @formatter:off
        List<String> fields = perameterNames.stream()
            .filter(paramName -> paramName.equals(FACET_QUERY_PARAM_KEY))
            .map(request::getParameterValues)
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .map(s -> s.split(","))
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());
        return fields.stream().map(field -> {
            Optional<String> sort = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_SORT_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> pageSize = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_PAGE_SIZE_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> pageNumber = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_PAGE_NUMBER_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> type = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_TYPE_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> exclusionTag = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_EXCLUDE_TAG_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> rangeStart = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_RANGE_START_TAG_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> rangeEnd = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_RANGE_END_TAG_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> rangeGap = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FACET_RANGE_GAP_TAG_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            return FacetArg.of(field, sort, pageSize, pageNumber, type, exclusionTag, rangeStart, rangeEnd, rangeGap);
        }).collect(Collectors.toList());
        // @formatter:on
    }

    public static List<FilterArg> getFilterArguments(HttpServletRequest request) {
        List<String> perameterNames = Collections.list(request.getParameterNames());
        // @formatter:off
        List<String> fields = perameterNames.stream()
            .filter(paramName -> paramName.equals(FILTER_QUERY_PARAM_KEY))
            .map(request::getParameterValues)
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .map(s -> s.split(","))
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());
        List<FilterArg> filters = new ArrayList<FilterArg>();
        filters = fields.stream().map(field -> {
            Optional<String> value = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FILTER_VALUE_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> opKey = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FILTER_OPKEY_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();
            Optional<String> tag = perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FILTER_TAG_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .findAny();                
            return FilterArg.of(field, value, opKey, tag);
        }).collect(Collectors.toList());
        // @formatter:on
        return filters;
    }

    public static List<BoostArg> getBoostArguments(HttpServletRequest request) {
        // @formatter:off
        return Collections.list(request.getParameterNames()).stream()
            .filter(paramName -> paramName.equals(BOOST_QUERY_PARAM_KEY))
            .map(request::getParameterValues)
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .map(BoostArg::of)
            .collect(Collectors.toList());
        // @formatter:on
    }

    public static HighlightArg getHightlightArgument(HttpServletRequest request) {
        String fields = request.getParameter(HIGHLIGHT_FIELDS_QUERY_PARAM_KEY);
        Optional<String> prefix = Optional.ofNullable(request.getParameter(HIGHLIGHT_PRE_QUERY_PARAM_KEY));
        Optional<String> postfix = Optional.ofNullable(request.getParameter(HIGHLIGHT_POST_QUERY_PARAM_KEY));
        return HighlightArg.of(StringUtils.isNotEmpty(fields) ? fields.split(",") : new String[] {}, prefix, postfix);
    }

    public static QueryArg getQueryArgument(HttpServletRequest request) {
        Optional<String> expression = Optional.ofNullable(request.getParameter(QUERY_EXPRESSION_QUERY_PARAM_KEY));
        Optional<String> defaultField = Optional.ofNullable(request.getParameter(DEFAULT_FIELD_QUERY_PARAM_KEY));
        Optional<String> minimumShouldMatch = Optional.ofNullable(request.getParameter(MINIMUM_SHOULD_MATCH_QUERY_PARAM_KEY));
        Optional<String> queryField = Optional.ofNullable(request.getParameter(QUERY_FIELD_QUERY_PARAM_KEY));
        Optional<String> boostQuery = Optional.ofNullable(request.getParameter(BOOST_QUERY_QUERY_PARAM_KEY));
        Optional<String> fields = Optional.ofNullable(request.getParameter(FIELDS_QUERY_PARAM_KEY));
        return QueryArg.of(expression, defaultField, minimumShouldMatch, queryField, boostQuery, fields);
    }

}
