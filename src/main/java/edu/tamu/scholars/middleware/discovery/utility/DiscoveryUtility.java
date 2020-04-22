package edu.tamu.scholars.middleware.discovery.utility;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DISCOVERY_MODEL_PACKAGE;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.PATH_DELIMETER_REGEX;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.REQUEST_PARAM_DELIMETER;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;

public class DiscoveryUtility {

    private final static Set<Class<?>> DISCOVERY_DOCUMENT_TYPES = new HashSet<>();

    private final static BidiMap<String, String> DISCOVERY_DOCUMENT_PROPERTY_PATH_MAPPING = new DualHashBidiMap<String, String>();

    static {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(CollectionSource.class));
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(DISCOVERY_MODEL_PACKAGE);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                DISCOVERY_DOCUMENT_TYPES.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find class for " + beanDefinition.getBeanClassName(), e);
            }
        }
    }

    public static Set<Class<?>> getDiscoveryDocumentTypes() {
        return DISCOVERY_DOCUMENT_TYPES;
    }

    public static Class<?> getDiscoveryDocumentTypeByName(String name) {
        String typeName = String.format("%s.%s", DISCOVERY_MODEL_PACKAGE, name);
        Optional<Class<?>> documentType = DISCOVERY_DOCUMENT_TYPES.stream().filter(type -> type.getName().equals(typeName)).findAny();
        if (documentType.isPresent()) {
            return documentType.get();
        }
        throw new RuntimeException("Unable to find class for " + name);
    }

    public static String[] processFields(String[] fields) {
        return Arrays.asList(fields).stream().map(DiscoveryUtility::findProperty).collect(Collectors.toList()).toArray(new String[fields.length]);
    }

    public static String processFields(String fields) {
        if (StringUtils.isNoneEmpty(fields)) {
            String parameter = StringUtils.EMPTY;
            for (String field : fields.split(REQUEST_PARAM_DELIMETER)) {
                parameter += findProperty(field) + REQUEST_PARAM_DELIMETER;
            }
            return StringUtils.removeEnd(parameter, REQUEST_PARAM_DELIMETER);
        }
        return StringUtils.EMPTY;
    }

    public static String findPath(String property) {
        String actualProperty = DISCOVERY_DOCUMENT_PROPERTY_PATH_MAPPING.getKey(property);
        if (StringUtils.isNoneEmpty(actualProperty)) {
            return actualProperty;
        }
        return property;
    }

    public static String findProperty(String path) {
        String actualPath = DISCOVERY_DOCUMENT_PROPERTY_PATH_MAPPING.get(path);
        if (StringUtils.isNotEmpty(actualPath)) {
            return actualPath;
        }
        List<String> properties = new ArrayList<String>(Arrays.asList(path.split(PATH_DELIMETER_REGEX)));
        for (Class<?> type : DISCOVERY_DOCUMENT_TYPES) {
            Optional<String> property = findProperty(type, properties);
            if (property.isPresent()) {
                DISCOVERY_DOCUMENT_PROPERTY_PATH_MAPPING.put(path, property.get());
                return property.get();
            }
        }
        DISCOVERY_DOCUMENT_PROPERTY_PATH_MAPPING.put(path, path);
        return path;
    }

    private static Optional<String> findProperty(Class<?> type, List<String> properties) {
        String property = properties.get(0);
        Field field = FieldUtils.getField(type, property, true);
        if (field != null) {
            if (properties.size() == 1) {
                return Optional.of(field.getName());
            }
            NestedObject nestedObject = field.getAnnotation(NestedObject.class);
            if (nestedObject != null) {
                String referenceProperty = properties.get(1);
                for (Reference reference : nestedObject.properties()) {
                    if (reference.key().equals(referenceProperty)) {
                        properties.remove(0);
                        properties.set(0, reference.value());
                        return findProperty(type, properties);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
