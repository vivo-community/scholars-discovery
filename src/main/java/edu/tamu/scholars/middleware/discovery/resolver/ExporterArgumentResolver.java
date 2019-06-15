package edu.tamu.scholars.middleware.discovery.resolver;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import edu.tamu.scholars.middleware.discovery.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.discovery.service.export.Exporter;

public final class ExporterArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String EXPORTER_QUERY_PARAM_KEY = "type";

    private final static String DEFAULT_EXPORTER_TYPE = "csv";

    private final List<Exporter> exporters;

    public ExporterArgumentResolver(List<Exporter> exporters) {
        this.exporters = exporters;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Exporter.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Optional<String> requestedType = Optional.ofNullable(request.getParameter(EXPORTER_QUERY_PARAM_KEY));
        final String type = requestedType.isPresent() ? requestedType.get() : DEFAULT_EXPORTER_TYPE;
        Optional<Exporter> exporter = exporters.stream().filter(e -> e.type().equals(type)).findAny();
        if (exporter.isPresent()) {
            return exporter.get();
        }
        throw new UnknownExporterTypeException(String.format("Could not find exporter of type %s", type));
    }

}
