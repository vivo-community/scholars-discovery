package edu.tamu.scholars.middleware.discovery.resource;

import org.springframework.hateoas.Link;

import edu.tamu.scholars.middleware.discovery.model.Individual;

public class IndividualResource extends AbstractSolrDocumentResource<Individual> {

    public IndividualResource(Individual individual, Iterable<Link> links) {
        super(individual, links);
    }

}
