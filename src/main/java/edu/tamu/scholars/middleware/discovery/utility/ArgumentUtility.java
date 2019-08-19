package edu.tamu.scholars.middleware.discovery.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;

public class ArgumentUtility {

    private final static String FACET_QUERY_PARAM_KEY = "facets";
    private final static String FILTER_QUERY_PARAM_KEY = "filters";
    private final static String INDEX_QUERY_PARAM_KEY = "index";

    private final static String FACET_SORT_FORMAT = "%s.sort";
    private final static String FACET_PAGE_SIZE_FORMAT = "%s.pageSize";
    private final static String FACET_PAGE_NUMBER_FORMAT = "%s.pageNumber";
    private final static String FACET_TYPE_FORMAT = "%s.type";

    private final static String FILTER_FORMAT = "%s.filter";

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
            return FacetArg.of(field, sort, pageSize, pageNumber, type);
        }).collect(Collectors.toList());
        // @formatter:on
    }

    public static List<FilterArg> getFilterArguments(HttpServletRequest request) {
        List<String> perameterNames = Collections.list(request.getParameterNames());
        // @formatter:off
        List<String> fields = perameterNames.stream()
            .filter(paramName -> paramName.equals(FACET_QUERY_PARAM_KEY) || paramName.equals(FILTER_QUERY_PARAM_KEY))
            .map(request::getParameterValues)
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .map(s -> s.split(","))
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());
        List<FilterArg> filters = new ArrayList<FilterArg>();
        fields.forEach(field -> {
            filters.addAll(perameterNames.stream()
                .filter(paramName -> paramName.equals(String.format(FILTER_FORMAT, field)))
                .map(request::getParameterValues)
                .map(Arrays::asList)
                .flatMap(list -> list.stream())
                .map(value -> FilterArg.of(field, value))
                .collect(Collectors.toList()));
        });
        // @formatter:on
        return filters;
    }

    public static Optional<IndexArg> getIndexArgument(HttpServletRequest request) {
        // @formatter:off
        return Collections.list(request.getParameterNames()).stream()
            .filter(paramName -> paramName.equals(INDEX_QUERY_PARAM_KEY))
            .map(request::getParameterValues)
            .map(Arrays::asList)
            .flatMap(list -> list.stream())
            .map(IndexArg::of)
            .findAny();
        // @formatter:on
    }

}
