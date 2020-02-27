package edu.tamu.scholars.middleware.graphql.service;

import org.springframework.data.domain.PageRequest;

import java.util.Map;
import java.util.HashMap;

import io.leangen.graphql.metadata.strategy.value.DefaultValueProvider;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;

public class DefaultPageRequestProvider implements DefaultValueProvider {

    public Object getDefaultValue(AnnotatedElement targetElement, AnnotatedType type, 
        Object initialValue) {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("pageNumber", new Integer(0));
        values.put("pageSize", new Integer(100));
        values.put("sort", null); // TODO: is this correct? or "{}"
        return values;
    }
}