package edu.tamu.scholars.middleware.discovery.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

public abstract class AbstractSolrDocumentSerializer<D extends AbstractSolrDocument> extends StdSerializer<D> {

    private static final long serialVersionUID = -7465695732226901511L;

    public AbstractSolrDocumentSerializer(Class<D> t) {
        super(t);
    }

    protected abstract JsonSerializer<D> getDelegate();

    @Override
    public void serialize(D document, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        getDelegate().serialize(document, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndObject();
    }

}
