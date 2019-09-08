package edu.tamu.scholars.middleware.discovery.utility;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DISCOVERY_MODEL_PACKAGE;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;

public class DiscoveryUtility {

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

    public static Class<?> getDiscoveryDocumentTypeByName(String name) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(CollectionSource.class));
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(DISCOVERY_MODEL_PACKAGE);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                if (beanDefinition.getBeanClassName().equals(String.format("%s.%s", DISCOVERY_MODEL_PACKAGE, name))) {
                    return Class.forName(beanDefinition.getBeanClassName());
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find class for " + beanDefinition.getBeanClassName(), e);
            }
        }
        throw new RuntimeException("Unable to find class for " + name);
    }

}
