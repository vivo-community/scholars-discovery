package edu.tamu.scholars.middleware.discovery.serializer;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;

import edu.tamu.scholars.middleware.discovery.model.Individual;

@JsonComponent
public class IndividualSerializer extends StdSerializer<Individual> {

    private static final long serialVersionUID = -7465695732226901511L;

    private final JsonSerializer<Individual> delegate = new UnwrappingIndividualSerializer(NameTransformer.NOP);

    public IndividualSerializer() {
        super(Individual.class);
    }

    @Override
    public void serialize(Individual document, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        delegate.serialize(document, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndObject();
    }

    @Override
    public JsonSerializer<Individual> unwrappingSerializer(final NameTransformer nameTransformer) {
        return new UnwrappingIndividualSerializer(nameTransformer);
    }

}
