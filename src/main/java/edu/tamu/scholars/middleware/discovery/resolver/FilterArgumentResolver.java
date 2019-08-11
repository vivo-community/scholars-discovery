package edu.tamu.scholars.middleware.discovery.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import edu.tamu.scholars.middleware.discovery.argument.FilterArg;

public class FilterArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String FACET_QUERY_PARAM_KEY = "facets";
    private final static String FILTER_QUERY_PARAM_KEY = "filters";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
        return resolvableType.hasGenerics() && FilterArg.class.isAssignableFrom(resolvableType.getGeneric(0).resolve());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        List<String> fields = Collections.list(request.getParameterNames()).stream().filter(paramName -> paramName.equals(FACET_QUERY_PARAM_KEY) || paramName.equals(FILTER_QUERY_PARAM_KEY)).map(request::getParameterValues).map(Arrays::asList).flatMap(list -> list.stream()).map(s -> s.split(",")).map(Arrays::asList).flatMap(list -> list.stream()).collect(Collectors.toList());
        List<FilterArg> filters = new ArrayList<FilterArg>();
        fields.forEach(field -> {
            filters.addAll(Collections.list(request.getParameterNames()).stream().filter(paramName -> paramName.equals(String.format("%s.%s", field, "filter"))).map(request::getParameterValues).map(Arrays::asList).flatMap(list -> list.stream()).map(value -> FilterArg.of(field, value)).collect(Collectors.toList()));
        });
        return filters;
    }

}
