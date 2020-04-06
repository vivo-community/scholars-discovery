package edu.tamu.scholars.middleware.discovery.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;

import edu.tamu.scholars.middleware.discovery.model.Individual;

public class IndividualResource extends EntityModel<Individual> {

    public IndividualResource(Individual individual, Iterable<Link> links) {
        super(individual, links);
    }

}
