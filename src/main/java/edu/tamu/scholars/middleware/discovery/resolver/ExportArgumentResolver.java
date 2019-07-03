package edu.tamu.scholars.middleware.discovery.resolver;

import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import edu.tamu.scholars.middleware.discovery.exception.ExportQueryParameterRequiredException;
import edu.tamu.scholars.middleware.discovery.service.export.Export;

public final class ExportArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String EXPORT_QUERY_PARAM_KEY = "export";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Export.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Optional<Export> export = Collections.list(request.getParameterNames()).stream()
                .filter(paramName -> paramName.equals(EXPORT_QUERY_PARAM_KEY))
                .map(request::getParameterValues).map(Export::new).findAny();
        if (export.isPresent()) {
            return export.get();
        }
        throw new ExportQueryParameterRequiredException("Export query parameter is required");
    }

}
