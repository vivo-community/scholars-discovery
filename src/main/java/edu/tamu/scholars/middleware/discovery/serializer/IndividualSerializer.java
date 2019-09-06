package edu.tamu.scholars.middleware.discovery.serializer;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;

import edu.tamu.scholars.middleware.discovery.model.Individual;

@JsonComponent
public class IndividualSerializer extends AbstractSolrDocumentSerializer<Individual> {

    private static final long serialVersionUID = -3125704658194129373L;

    private final JsonSerializer<Individual> delegate = new UnwrappingIndividualSerializer(NameTransformer.NOP);

    public IndividualSerializer() {
        super(Individual.class);
    }

    @Override
    protected JsonSerializer<Individual> getDelegate() {
        return delegate;
    }

    @Override
    public JsonSerializer<Individual> unwrappingSerializer(final NameTransformer nameTransformer) {
        return new UnwrappingIndividualSerializer(nameTransformer);
    }

}
