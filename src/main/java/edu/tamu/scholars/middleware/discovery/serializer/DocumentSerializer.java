package edu.tamu.scholars.middleware.discovery.serializer;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;

import edu.tamu.scholars.middleware.discovery.model.Document;

@JsonComponent
public class DocumentSerializer extends AbstractSolrDocumentSerializer<Document> {

    private static final long serialVersionUID = 8958373904891340032L;

    private final JsonSerializer<Document> delegate = new UnwrappingDocumentSerializer(NameTransformer.NOP);

    public DocumentSerializer() {
        super(Document.class);
    }

    @Override
    protected JsonSerializer<Document> getDelegate() {
        return delegate;
    }

    @Override
    public JsonSerializer<Document> unwrappingSerializer(final NameTransformer nameTransformer) {
        return new UnwrappingDocumentSerializer(nameTransformer);
    }

}
