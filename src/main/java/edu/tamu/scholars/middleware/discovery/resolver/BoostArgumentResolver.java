package edu.tamu.scholars.middleware.discovery.resolver;

import static edu.tamu.scholars.middleware.discovery.utility.ArgumentUtility.getBoostArguments;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;

public class BoostArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
        return resolvableType.hasGenerics() && BoostArg.class.isAssignableFrom(resolvableType.getGeneric(0).resolve());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return getBoostArguments(request);
    }

}
