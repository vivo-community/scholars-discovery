package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.assembler.IndividualResourceAssembler;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;

@RepositoryRestController
@RequestMapping("/individuals")
public class IndividualController extends AbstractSolrDocumentController<Individual, IndividualRepo, IndividualResource, IndividualResourceAssembler> {

}
