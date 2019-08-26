package edu.tamu.scholars.middleware.graphql.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.type.TypeFactory;

import edu.tamu.scholars.middleware.discovery.AbstractSolrDocumentIntegrationTest;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import graphql.language.Field;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public abstract class AbstractNestedDocumentServiceTest<ND extends AbstractNestedDocument, D extends AbstractSolrDocument, R extends SolrDocumentRepo<D>, DAO extends AbstractNestedDocumentService<ND, D, R>> extends AbstractSolrDocumentIntegrationTest<D, R> {

    @Autowired
    private DAO service;

    @Override
    @BeforeAll
    public void setup() throws IOException {
        super.setup();
        service.getComposites().stream().filter(c -> c.getType().equals(repo.type().getSimpleName())).forEach(c -> {
            c.getReferences().stream().forEach(r -> {
                String collection = DiscoveryUtility.getDiscoveryDocumentTypeByName(r.getType()).getAnnotation(SolrDocument.class).collection();
                createCore(collection);
            });
        });
    }

    @Override
    @AfterAll
    public void cleanup() throws IOException {
        super.cleanup();
        service.getComposites().stream().filter(c -> c.getType().equals(repo.type().getSimpleName())).forEach(c -> {
            c.getReferences().stream().forEach(r -> {
                String collection = DiscoveryUtility.getDiscoveryDocumentTypeByName(r.getType()).getAnnotation(SolrDocument.class).collection();
                deleteCore(collection);
            });
        });
    }

    @Test
    public void testGeneratedDocumentDefaultConstructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<?> clazz = getNestedDocumentType();

        @SuppressWarnings("unchecked")
        ND document = (ND) clazz.getConstructor().newInstance();

        assertNotNull(document);
    }

    @Test
    public void testGeneratedDocuments() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        testGeneratedDocument(getNestedDocumentType());
    }

    @Test
    public void testFindById() throws IOException {
        mockDocuments.forEach(mockDocument -> {
            String id = mockDocument.getId();
            Optional<ND> nestedDocument = service.findById(id, getGraphQLEnvironmentFields());
            assertTrue(nestedDocument.isPresent());
            assertTrue(nestedDocument.get() instanceof AbstractNestedDocument);
        });
    }

    @Test
    public void testSave() throws IOException {
        assertThrows(UnsupportedOperationException.class, () -> {
            mockDocuments.forEach(mockDocument -> {
                String id = mockDocument.getId();
                ND nestedDocument = service.findById(id, getGraphQLEnvironmentFields()).get();
                String newId = String.format("%s-updated", nestedDocument.getId());
                nestedDocument.setId(newId);
                nestedDocument = service.save(nestedDocument);
                assertEquals(newId, nestedDocument.getId());
                assertTrue(nestedDocument instanceof AbstractNestedDocument);
            });
        });
    }

    @Test
    public void testFindAll() throws IOException {
        List<ND> nestedDocuments = StreamSupport.stream(service.findAll(getGraphQLEnvironmentFields()).spliterator(), false).collect(Collectors.toList());
        assertEquals(3, nestedDocuments.size());
        nestedDocuments.forEach(nestedDocument -> {
            assertTrue(nestedDocument instanceof AbstractNestedDocument);
        });
    }

    @Test
    public void testSaveAll() throws IOException {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<ND> nestedDocuments = StreamSupport.stream(service.findAll().spliterator(), false).collect(Collectors.toList());
            nestedDocuments.forEach(nestedDocument -> {
                nestedDocument.setId(String.format("%s-updated", nestedDocument.getId()));
            });
            nestedDocuments = StreamSupport.stream(service.saveAll(nestedDocuments).spliterator(), false).collect(Collectors.toList());
            nestedDocuments.forEach(nestedDocument -> {
                assertTrue(nestedDocument.getId().endsWith("-updated"));
                assertTrue(nestedDocument instanceof AbstractNestedDocument);
            });
        });
    }

    // TODO: test other methods of AbstractNestedDocumentService

    protected abstract List<Field> getGraphQLEnvironmentFields();

    protected abstract Class<?> getNestedDocumentType();

    private AbstractNestedDocument testGeneratedDocument(Class<?> type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        AbstractNestedDocument document = (AbstractNestedDocument) type.getConstructor().newInstance();

        List<String> listOfStrings = Arrays.asList(new String[] { "Hello", "World" });

        Set<String> setOfStrings = new HashSet<String>(listOfStrings);

        List<java.lang.reflect.Field> fields = FieldUtils.getAllFieldsList(type).stream().filter(field -> !field.getName().equals("serialVersionUID")).filter(field -> !field.getName().startsWith("$")).collect(Collectors.toList());

        for (java.lang.reflect.Field field : fields) {
            String property = field.getName();
            if (AbstractNestedDocument.class.isAssignableFrom(field.getType())) {
                AbstractNestedDocument nestedDocument = testGeneratedDocument(field.getType());
                MethodUtils.invokeMethod(document, true, setter(property), nestedDocument);
                assertEquals(nestedDocument, MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (List.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type actualType = parameterizedType.getActualTypeArguments()[0];
                    Class<?> actualClass = TypeFactory.rawClass(actualType);
                    if (AbstractNestedDocument.class.isAssignableFrom(actualClass)) {
                        List<AbstractNestedDocument> listOfNestedDocuments = new ArrayList<AbstractNestedDocument>();
                        AbstractNestedDocument nestedDocument = testGeneratedDocument(actualClass);
                        listOfNestedDocuments.add(nestedDocument);
                        MethodUtils.invokeMethod(document, true, setter(property), listOfNestedDocuments);
                        assertEquals(listOfNestedDocuments, MethodUtils.invokeMethod(document, true, getter(property)));
                    } else if (String.class.isAssignableFrom(actualClass)) {
                        MethodUtils.invokeMethod(document, true, setter(property), listOfStrings);
                        assertEquals(listOfStrings, MethodUtils.invokeMethod(document, true, getter(property)));
                    }
                }
            } else if (Set.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), setOfStrings);
                assertEquals(setOfStrings, MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (String.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), "Test");
                assertEquals("Test", MethodUtils.invokeMethod(document, true, getter(property)));
            } else {
                throw new RuntimeException("Unexpected generated type");
            }
        }

        return document;
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
