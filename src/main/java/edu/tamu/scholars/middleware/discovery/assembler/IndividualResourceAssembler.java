package edu.tamu.scholars.middleware.discovery.assembler;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import edu.tamu.scholars.middleware.discovery.controller.IndividualController;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;

@Component
public class IndividualResourceAssembler extends AbstractSolrDocumentResourceAssembler<Individual, IndividualResource> {

    public IndividualResourceAssembler() {
        super(IndividualController.class, IndividualResource.class);
    }

    @Override
    protected IndividualResource createResource(Individual collection, Iterable<Link> links) {
        return new IndividualResource(collection, links);
    }

}
