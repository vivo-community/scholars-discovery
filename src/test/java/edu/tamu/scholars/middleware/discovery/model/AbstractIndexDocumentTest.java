package edu.tamu.scholars.middleware.discovery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.discovery.annotation.FieldType;

@ExtendWith(SpringExtension.class)
public abstract class AbstractIndexDocumentTest<D extends AbstractIndexDocument> {

    @Test
    public void testDefaultConstructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<?> clazz = getType();

        @SuppressWarnings("unchecked")
        D document = (D) clazz.getConstructor().newInstance();

        assertNotNull(document);
    }

    @Test
    public void testGettersAndSetters() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Class<?> clazz = getType();

        @SuppressWarnings("unchecked")
        D document = (D) clazz.getConstructor().newInstance();

        List<String> list = Arrays.asList(new String[] { "Hello", "World" });

        Set<String> set = new HashSet<String>(list);

        // NOTE: only gets field annotated with @Indexed, which is all fields of a AbstractSolrDocument
        for (Field field : FieldUtils.getFieldsListWithAnnotation(clazz, FieldType.class)) {
            String property = field.getName();
            if (List.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), list);
                assertEquals(list, MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (Set.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), set);
                assertEquals(set, MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (String.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), "Test");
                assertEquals("Test", MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (Integer.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), 1);
                assertEquals(1, MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (Float.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), 2f);
                assertEquals(2f, MethodUtils.invokeMethod(document, true, getter(property)));
            } else if (Double.class.isAssignableFrom(field.getType())) {
                MethodUtils.invokeMethod(document, true, setter(property), 3.2);
                assertEquals(3.2, MethodUtils.invokeMethod(document, true, getter(property)));
            } else {
                throw new RuntimeException("Unexpected type");
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

    protected abstract Class<?> getType();

}
