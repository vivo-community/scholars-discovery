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

public class ArgumentUtility {

    private final static String FACET_QUERY_PARAM_KEY = "facets";
    private final static String FILTER_QUERY_PARAM_KEY = "filters";
    private final static String BOOST_QUERY_PARAM_KEY = "boost";

    private final static String HIGHLIGHT_FIELDS_QUERY_PARAM_KEY = "hl";
    private final static String HIGHLIGHT_PRE_QUERY_PARAM_KEY = "hl.pre";
    private final static String HIGHLIGHT_POST_QUERY_PARAM_KEY = "hl.post";

    private final static String FACET_SORT_FORMAT = "%s.sort";
    private final static String FACET_PAGE_SIZE_FORMAT = "%s.pageSize";
    private final static String FACET_PAGE_NUMBER_FORMAT = "%s.pageNumber";
    private final static String FACET_TYPE_FORMAT = "%s.type";
    private final static String FACET_EXCLUDE_TAG_FORMAT = "%s.exclusionTag";

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
            return FacetArg.of(field, sort, pageSize, pageNumber, type, exclusionTag);
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
        Optional<String> pre = Optional.ofNullable(request.getParameter(HIGHLIGHT_PRE_QUERY_PARAM_KEY));
        Optional<String> post = Optional.ofNullable(request.getParameter(HIGHLIGHT_POST_QUERY_PARAM_KEY));
        return HighlightArg.of(StringUtils.isNotEmpty(fields) ? fields : StringUtils.EMPTY, pre, post);
    }

}
