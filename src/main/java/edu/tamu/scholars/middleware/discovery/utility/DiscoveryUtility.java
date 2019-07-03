package edu.tamu.scholars.middleware.discovery.utility;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DISCOVERY_MODEL_PACKAGE;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.LABEL;
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
import org.springframework.data.solr.core.mapping.SolrDocument;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;

public class DiscoveryUtility {

    public static List<String> getFieldNames(String collection) {
        List<String> fields = new ArrayList<String>();
        fields.add(ID);
        for (BeanDefinition beanDefinition : getSolrDocumentBeanDefinitions()) {
            try {
                Class<?> type = Class.forName(beanDefinition.getBeanClassName());
                for (Field field : FieldUtils.getFieldsListWithAnnotation(type, PropertySource.class)) {
                    fields.add(field.getName());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    public static boolean hasIndexField(String collection, String field) {
        Optional<Class<?>> type = getCollectionType(collection);
        if (type.isPresent()) {
            for (Field f : FieldUtils.getFieldsListWithAnnotation(type.get(), PropertySource.class)) {
                if (String.class.isAssignableFrom(f.getType()) && f.getName().equals(field)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Optional<Class<?>> getCollectionType(String collection) {
        for (BeanDefinition beanDefinition : getSolrDocumentBeanDefinitions()) {
            try {
                Class<?> type = Class.forName(beanDefinition.getBeanClassName());
                SolrDocument solrDocument = type.getAnnotation(SolrDocument.class);
                if (collection.equals(solrDocument.collection())) {
                    return Optional.of(type);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public static boolean isCollection(String collection) {
        for (BeanDefinition beanDefinition : getSolrDocumentBeanDefinitions()) {
            try {
                Class<?> type = Class.forName(beanDefinition.getBeanClassName());
                SolrDocument solrDocument = type.getAnnotation(SolrDocument.class);
                if (collection.equals(solrDocument.collection())) {
                    return true;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Set<Class<?>> getDiscoveryDocumentTypes() {
        Set<Class<?>> documents = new HashSet<Class<?>>();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(CollectionSource.class));
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(DISCOVERY_MODEL_PACKAGE);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                documents.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find class for " + beanDefinition.getBeanClassName(), e);
            }
        }
        return documents;
    }

    public static String findProperty(String type, String path) {
        String classPath = String.format("%s.%s", DISCOVERY_MODEL_PACKAGE, type);
        try {
            Class<?> documentType = Class.forName(classPath);
            List<String> properties = new ArrayList<String>(Arrays.asList(path.split(PATH_DELIMETER_REGEX)));
            return findProperty(documentType, properties);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Unable to find class %s", classPath), e);
        }
    }

    public static String findProperty(Class<?> type, List<String> properties) {
        String property = properties.get(0);
        Field field = FieldUtils.getField(type, property, true);
        properties.remove(0);
        if (properties.isEmpty()) {
            return field.getName();
        }
        NestedObject nestedObject = field.getAnnotation(NestedObject.class);
        String referenceProperty = properties.get(0);
        if (nestedObject != null) {
            for (Reference reference : nestedObject.value()) {
                if (reference.key().equals(referenceProperty)) {
                    properties.set(0, reference.value());
                    return findProperty(type, properties);
                }
            }
            throw new RuntimeException(String.format("Unable to find reference property %s of class %s", referenceProperty, type.getSimpleName()));
        } else {
            throw new RuntimeException(String.format("No nested object annotation found on property %s of class %s", property, type.getSimpleName()));
        }
    }

    public static Field findField(Class<?> clazz, String[] path) throws InvalidValuePathException {
        if (path.length == 1 || path[1].equals(LABEL) || path[1].equals(ID)) {
            return findField(clazz, path[0]);
        }
        Field field = findField(clazz, path[0]);
        return getReferenceField(field, Arrays.copyOfRange(path, 1, path.length));
    }

    public static Field getReferenceField(Field field, String[] path) throws InvalidValuePathException {
        NestedObject nested = field.getAnnotation(NestedObject.class);
        for (Reference reference : nested.value()) {
            if (reference.key().contentEquals(path[0])) {
                Field refField = findField(field.getDeclaringClass(), reference.value());
                return path.length > 1 ? getReferenceField(refField, Arrays.copyOfRange(path, 1, path.length)) : refField;
            }
        }
        throw new InvalidValuePathException(String.format("Unable to resolve %s reference %s", field.getName(), String.join(".", path)));
    }

    public static Field findField(Class<?> clazz, String property) throws InvalidValuePathException {
        try {
            return clazz.getDeclaredField(property);
        } catch (NoSuchFieldException | SecurityException e) {
            Class<?> superClazz = clazz.getSuperclass();
            if (superClazz != null) {
                return findField(superClazz, property);
            }
        }
        throw new InvalidValuePathException(String.format("Unable to resolve %s of %s", property, clazz.getSimpleName()));
    }

    private static Set<BeanDefinition> getSolrDocumentBeanDefinitions() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(SolrDocument.class));
        return provider.findCandidateComponents(DISCOVERY_MODEL_PACKAGE);
    }

}
