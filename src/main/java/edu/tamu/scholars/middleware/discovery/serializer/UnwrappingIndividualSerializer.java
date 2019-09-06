package edu.tamu.scholars.middleware.discovery.serializer;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.NameTransformer;

import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility;

public class UnwrappingIndividualSerializer extends AbstractUnwrappingSolrDocumentSerializer<Individual> {

    public UnwrappingIndividualSerializer(NameTransformer nameTransformer) {
        super(nameTransformer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(Individual document, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        Class<?> type = DiscoveryUtility.getDiscoveryDocumentTypeByName(document.getClazz());

        Map<String, List<String>> content = document.getContent();

        jsonGenerator.writeObjectField(nameTransformer.transform(ID), document.getId());

        for (Field field : FieldUtils.getFieldsListWithAnnotation(type, PropertySource.class)) {
            Object value = content.get(field.getName());
            if (value != null) {
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                String name = jsonProperty != null ? jsonProperty.value() : field.getName();
                NestedObject nestedObject = field.getAnnotation(NestedObject.class);
                if (nestedObject != null) {
                    if (nestedObject.root()) {
                        if (List.class.isAssignableFrom(field.getType())) {
                            List<String> values = (List<String>) value;
                            ArrayNode array = JsonNodeFactory.instance.arrayNode();
                            for (String v : values) {
                                String[] vParts = v.split(NESTED_DELIMITER);
                                if (vParts.length > 1) {
                                    array.add(processValue(content, type, field, vParts, 1));
                                }
                            }
                            if (array.size() > 0) {
                                jsonGenerator.writeObjectField(nameTransformer.transform(name), array);
                            }
                        } else {
                            String v = value.toString();
                            String[] vParts = v.split(NESTED_DELIMITER);
                            if (vParts.length > 1) {
                                ObjectNode node = processValue(content, type, field, vParts, 1);
                                jsonGenerator.writeObjectField(nameTransformer.transform(name), node);
                            }
                        }
                    }
                } else {
                    if (!value.toString().contains(NESTED_DELIMITER)) {
                        if (List.class.isAssignableFrom(field.getType())) {
                            jsonGenerator.writeObjectField(nameTransformer.transform(name), value);
                        } else {
                            List<String> values = (List<String>) value;
                            jsonGenerator.writeObjectField(nameTransformer.transform(name), values.get(0));
                        }
                    }
                }
            }
        }
    }

    private ObjectNode processValue(Map<String, List<String>> content, Class<?> type, Field field, String[] vParts, int index) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        NestedObject nestedObject = field.getAnnotation(NestedObject.class);
        if (nestedObject != null) {
            node.put(ID, vParts[index]);
            node.put(nestedObject.label(), vParts[0]);
            processNestedObject(content, type, nestedObject, node, vParts, index + 1);
        }
        return node;
    }

    private void processNestedObject(Map<String, List<String>> content, Class<?> type, NestedObject nestedObject, ObjectNode node, String[] vParts, int depth) {
        for (Reference reference : nestedObject.properties()) {
            String ref = reference.value();

            Field nestedField = FieldUtils.getField(type, ref, true);

            JsonProperty jsonProperty = nestedField.getAnnotation(JsonProperty.class);
            String name = jsonProperty != null ? jsonProperty.value() : reference.key();

            Object nestedValue = content.get(nestedField.getName());

            if (nestedValue != null) {
                if (List.class.isAssignableFrom(nestedField.getType())) {
                    @SuppressWarnings("unchecked")
                    List<String> nestedValues = (List<String>) nestedValue;
                    ArrayNode array = JsonNodeFactory.instance.arrayNode();
                    boolean multiValued = nestedField.getAnnotation(NestedMultiValuedProperty.class) != null;
                    for (String nv : nestedValues) {
                        String[] nvParts = nv.split(NESTED_DELIMITER);
                        if (nv.contains(vParts[depth - 1]) && !(vParts[0].equals(nvParts[0]))) {
                            if (nvParts.length > depth) {
                                ObjectNode subNode = processValue(content, type, nestedField, nvParts, depth);
                                array.add(subNode);
                            } else {
                                if (nvParts[0] != null) {
                                    array.add(nvParts[0]);
                                }
                            }
                            if (!multiValued) {
                                break;
                            }
                        }
                    }
                    if (array.size() > 0) {
                        if (multiValued) {
                            node.set(name, array);
                        } else {
                            node.set(name, array.get(0));
                        }
                    }
                } else {
                    String nv = nestedValue.toString();
                    String[] nvParts = nv.split(NESTED_DELIMITER);
                    if (nvParts.length > depth) {
                        ObjectNode subNode = processValue(content, type, nestedField, nvParts, depth);
                        node.set(name, subNode);
                    } else {
                        if (nvParts[0] != null) {
                            node.put(name, nvParts[0]);
                        }
                    }
                }
            }
        }
    }

}
