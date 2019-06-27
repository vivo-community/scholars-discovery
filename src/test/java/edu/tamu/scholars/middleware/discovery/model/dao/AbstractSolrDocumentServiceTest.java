package edu.tamu.scholars.middleware.discovery.model.dao;

import static edu.tamu.scholars.middleware.discovery.generator.NestedDocumentGenerator.DISCOVERY_GENERATED_MODEL_PACKAGE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.discovery.AbstractSolrDocumentIntegrationTest;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.doa.AbstractSolrDocumentService;
import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public abstract class AbstractSolrDocumentServiceTest<ND extends AbstractNestedDocument, D extends AbstractSolrDocument, R extends SolrDocumentRepo<D>, DAO extends AbstractSolrDocumentService<ND, D, R>> extends AbstractSolrDocumentIntegrationTest<D, R> {

    @Autowired
    private DAO service;

    @Test
    public void testGeneratedDocuments() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        for (Class<?> clazz : getGeneratedNestedDocuments()) {
            testGeneratedDocument(clazz);
        }
    }

    @Test
    public void testDAOFindById() throws IOException {
        mockDocuments.forEach(mockDocument -> {
            String id = mockDocument.getId();
            Optional<ND> document = service.findById(id);
            assertTrue(document.isPresent());
        });
    }

    @Test
    public void testDAOFindAll() throws IOException {
        List<ND> documents = StreamSupport.stream(service.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(3, documents.size());
    }

    private Set<Class<?>> getGeneratedNestedDocuments() {
        Set<Class<?>> documents = new HashSet<Class<?>>();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractNestedDocument.class));
        String packagePath = String.format("%s.%s", DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, getType().getSimpleName().toLowerCase());
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(packagePath);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                documents.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find class for " + beanDefinition.getBeanClassName(), e);
            }
        }
        return documents;
    }

    private void testGeneratedDocument(Class<?> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        @SuppressWarnings("unchecked")
        D document = (D) clazz.getConstructor().newInstance();

        String single = "Test";

        List<String> list = Arrays.asList(new String[] { "Hello", "World" });

        Set<String> set = new HashSet<String>(list);

        List<Field> fields = FieldUtils.getAllFieldsList(clazz).stream().filter(field -> !field.getName().equals("serialVersionUID")).filter(field -> !field.getName().contains("$")).collect(Collectors.toList());

        for (Field field : fields) {
            String property = field.getName();
            if (AbstractNestedDocument.class.isAssignableFrom(field.getType())) {
                testGeneratedDocument(field.getType());
            } else if (List.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), list);
                assertEquals(list, MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (Set.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), set);
                assertEquals(set, MethodUtils.invokeMethod(document, true, getter(property)));
            } else {
                MethodUtils.invokeMethod(document, true, setter(property), single);
                assertEquals(single, MethodUtils.invokeMethod(document, true, getter(property)));
            }
        }
    }

    private String getter(String property) {
        return getAccessor("get", property);
    }

    private String setter(String property) {
        return getAccessor("set", property);
    }

    private String getAccessor(String accessor, String property) {
        return accessor + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
    }

}
