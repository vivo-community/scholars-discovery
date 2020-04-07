package edu.tamu.scholars.middleware.discovery.assembler;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.tamu.scholars.middleware.discovery.controller.IndividualController;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;

@Component
public class IndividualResourceAssembler extends RepresentationModelAssemblerSupport<Individual, IndividualResource> {

    @Autowired
    private RepositoryEntityLinks repositoryEntityLinks;

    public IndividualResourceAssembler() {
        super(IndividualController.class, IndividualResource.class);
    }

    @Override
    public IndividualResource toModel(Individual document) {
        Link selfLink = repositoryEntityLinks.linkToItemResource(document.getClass(), document.getId()).withSelfRel();
        Link documentLink = repositoryEntityLinks.linkToCollectionResource(document.getClass());
        return new IndividualResource(document, Arrays.asList(selfLink, documentLink));
    }

}
