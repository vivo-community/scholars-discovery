package edu.tamu.scholars.middleware.graphql.provider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.util.HashMap;
import java.util.Map;

import edu.tamu.scholars.middleware.discovery.DiscoveryConstants;
import io.leangen.graphql.metadata.strategy.value.DefaultValueProvider;

public class DefaultQueryProvider implements DefaultValueProvider {

    public Object getDefaultValue(AnnotatedElement targetElement, AnnotatedType type, Object initialValue) {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("q", DiscoveryConstants.DEFAULT_QUERY);
        return values;
    }

}