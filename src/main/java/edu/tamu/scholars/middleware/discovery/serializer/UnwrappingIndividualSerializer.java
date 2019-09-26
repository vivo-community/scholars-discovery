package edu.tamu.scholars.middleware.discovery.serializer;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.CLASS;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;
import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.getDiscoveryDocumentTypeByName;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class UnwrappingIndividualSerializer extends JsonSerializer<Individual> {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final NameTransformer nameTransformer;

    public UnwrappingIndividualSerializer(final NameTransformer nameTransformer) {
        this.nameTransformer = nameTransformer;
    }

    @Override
    public boolean isUnwrappingSerializer() {
        return true;
    }

    @Override
    public void serialize(Individual document, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        Class<?> type = getDiscoveryDocumentTypeByName(document.getClazz());
        Map<String, List<String>> content = document.getContent();
        jsonGenerator.writeObjectField(nameTransformer.transform(ID), document.getId());
        jsonGenerator.writeObjectField(nameTransformer.transform(CLASS), document.getClazz());
        for (Field field : FieldUtils.getFieldsListWithAnnotation(type, PropertySource.class)) {
            Object value = content.get(field.getName());
            if (value != null) {
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                String name = nameTransformer.transform(jsonProperty != null ? jsonProperty.value() : field.getName());
                NestedObject nestedObject = field.getAnnotation(NestedObject.class);
                if (nestedObject != null) {
                    if (nestedObject.root()) {
                        if (List.class.isAssignableFrom(field.getType())) {

                            @SuppressWarnings("unchecked")
                            List<String> values = (List<String>) value;

                            // @formatter:off
                            ArrayNode array = values.parallelStream()
                                .map(v -> v.split(NESTED_DELIMITER))
                                .filter(vParts -> vParts.length > 1)
                                .map(vParts -> processValue(content, type, field, vParts, 1))
                                .collect(new JsonNodeArrayNodeCollector());
                            // @formatter:on

                            if (array.size() > 0) {
                                jsonGenerator.writeObjectField(name, array);
                            }
                        } else {
                            String[] vParts = strip(value.toString()).split(NESTED_DELIMITER);
                            if (vParts.length > 1) {
                                jsonGenerator.writeObjectField(name, processValue(content, type, field, vParts, 1));
                            }
                        }
                    }
                } else {
                    if (!value.toString().contains(NESTED_DELIMITER)) {
                        if (List.class.isAssignableFrom(field.getType())) {
                            jsonGenerator.writeObjectField(name, value);
                        } else {

                            @SuppressWarnings("unchecked")
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

                    if (nestedValues.size() > 0) {
                        boolean multiValued = nestedField.getAnnotation(NestedMultiValuedProperty.class) != null;

                        ArrayNode array;

                        // @formatter:off
                        if (strip(nestedValues.get(0)).split(NESTED_DELIMITER).length > depth) {
                            array = nestedValues.parallelStream()
                                .filter(nv -> isProperty(vParts, nv))
                                .map(nv -> processValue(content, type, nestedField, strip(nv).split(NESTED_DELIMITER), depth))
                                .collect(new JsonNodeArrayNodeCollector());
                        } else {
                            array = nestedValues.parallelStream()
                                .filter(nv -> isProperty(vParts, nv))
                                .map(nv -> strip(nv).split(NESTED_DELIMITER)[0])
                                .collect(new StringArrayNodeCollector());
                        }
                        // @formatter:on

                        if (array.size() > 0) {
                            if (multiValued) {
                                node.set(name, array);
                            } else {
                                node.set(name, array.get(0));
                            }
                        }
                    }
                } else {
                    String[] nvParts = strip(nestedValue.toString()).split(NESTED_DELIMITER);
                    if (nvParts.length > depth) {
                        node.set(name, processValue(content, type, nestedField, nvParts, depth));
                    } else {
                        if (nvParts[0] != null) {
                            node.put(name, nvParts[0]);
                        }
                    }
                }
            }
        }
    }

    private String strip(String value) {
        if (value.startsWith("[") && value.endsWith("]")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    private boolean isProperty(String[] parts, String value) {
        for (int i = parts.length - 1; i > 0; i--) {
            if (!value.contains(parts[i])) {
                return false;
            }
        }
        return true;
    }

    private class JsonNodeArrayNodeCollector implements Collector<JsonNode, ArrayNode, ArrayNode> {

        @Override
        public Supplier<ArrayNode> supplier() {
            return mapper::createArrayNode;
        }

        @Override
        public BiConsumer<ArrayNode, JsonNode> accumulator() {
            return ArrayNode::add;
        }

        @Override
        public BinaryOperator<ArrayNode> combiner() {
            return (x, y) -> {
                x.addAll(y);
                return x;
            };
        }

        @Override
        public Function<ArrayNode, ArrayNode> finisher() {
            return accumulator -> accumulator;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.UNORDERED);
        }

    }

    private class StringArrayNodeCollector implements Collector<String, ArrayNode, ArrayNode> {

        @Override
        public Supplier<ArrayNode> supplier() {
            return mapper::createArrayNode;
        }

        @Override
        public BiConsumer<ArrayNode, String> accumulator() {
            return ArrayNode::add;
        }

        @Override
        public BinaryOperator<ArrayNode> combiner() {
            return (x, y) -> {
                x.addAll(y);
                return x;
            };
        }

        @Override
        public Function<ArrayNode, ArrayNode> finisher() {
            return accumulator -> accumulator;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.UNORDERED);
        }

    }

}
