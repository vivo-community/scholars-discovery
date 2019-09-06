package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;

@RepositoryRestController
@RequestMapping("/individuals")
public class IndividualExportController extends AbstractSolrDocumentExportController<Individual, IndividualRepo> {

}
