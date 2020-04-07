package edu.tamu.scholars.middleware.discovery.utility;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DISCOVERY_MODEL_PACKAGE;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.PATH_DELIMETER_REGEX;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;

public class DiscoveryUtility {

    private final static Set<Class<?>> DISCOVERY_DOCUMENT_TYPES = new HashSet<Class<?>>();

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

    public static String findProperty(String path) {
        List<String> properties = new ArrayList<String>(Arrays.asList(path.split(PATH_DELIMETER_REGEX)));
        for (Class<?> type : DISCOVERY_DOCUMENT_TYPES) {
            Optional<String> property = findProperty(type, properties);
            if (property.isPresent()) {
                return property.get();
            }
        }
        return path;
    }

    private static Optional<String> findProperty(Class<?> type, List<String> properties) {
        String property = properties.get(0);
        Field field = FieldUtils.getField(type, property, true);
        if (field != null) {
            properties.remove(0);
            if (properties.isEmpty()) {
                return Optional.of(field.getName());
            }
            NestedObject nestedObject = field.getAnnotation(NestedObject.class);
            if (nestedObject != null) {
                String referenceProperty = properties.get(0);
                for (Reference reference : nestedObject.properties()) {
                    if (reference.key().equals(referenceProperty)) {
                        properties.set(0, reference.value());
                        return findProperty(type, properties);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
