package edu.tamu.scholars.middleware.discovery.serializer;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;

import edu.tamu.scholars.middleware.discovery.model.Person;

@JsonComponent
public class PersonSerializer extends AbstractSolrDocumentSerializer<Person> {

    private static final long serialVersionUID = -7319542138601723879L;

    private final JsonSerializer<Person> delegate = new UnwrappingPersonSerializer(NameTransformer.NOP);

    public PersonSerializer() {
        super(Person.class);
    }

    @Override
    protected JsonSerializer<Person> getDelegate() {
        return delegate;
    }

    @Override
    public JsonSerializer<Person> unwrappingSerializer(final NameTransformer nameTransformer) {
        return new UnwrappingPersonSerializer(nameTransformer);
    }

}
