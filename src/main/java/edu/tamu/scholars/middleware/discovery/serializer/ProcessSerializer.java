package edu.tamu.scholars.middleware.discovery.serializer;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;

import edu.tamu.scholars.middleware.discovery.model.Process;

@JsonComponent
public class ProcessSerializer extends AbstractSolrDocumentSerializer<Process> {

    private static final long serialVersionUID = -8645287381245561038L;

    private final JsonSerializer<Process> delegate = new UnwrappingProcessSerializer(NameTransformer.NOP);

    public ProcessSerializer() {
        super(Process.class);
    }

    @Override
    protected JsonSerializer<Process> getDelegate() {
        return delegate;
    }

    @Override
    public JsonSerializer<Process> unwrappingSerializer(final NameTransformer nameTransformer) {
        return new UnwrappingProcessSerializer(nameTransformer);
    }

}
