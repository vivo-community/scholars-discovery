package edu.tamu.scholars.middleware.discovery.utility;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.LABEL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.solr.core.mapping.SolrDocument;

import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;

public class DiscoveryUtility {

    private static final String DISCOVERY_MODEL_PACKAGE = "edu.tamu.scholars.middleware.discovery.model";

    public static List<String> getFields(String collection) {
        List<String> fields = new ArrayList<String>();
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

    private static Optional<Class<?>> getCollectionType(String collection) {
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

}
