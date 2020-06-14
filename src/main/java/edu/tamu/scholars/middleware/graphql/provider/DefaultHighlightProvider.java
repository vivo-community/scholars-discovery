package edu.tamu.scholars.middleware.graphql.provider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.leangen.graphql.metadata.strategy.value.DefaultValueProvider;

public class DefaultHighlightProvider implements DefaultValueProvider {

    public Object getDefaultValue(AnnotatedElement targetElement, AnnotatedType type, Object initialValue) {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("fields", new ArrayList<String>());
        return values;
    }

}