package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.model.Concept;
import edu.tamu.scholars.middleware.discovery.model.repo.ConceptRepo;

@RepositoryRestController
@RequestMapping("/concepts")
public class ConceptExportController extends AbstractSolrDocumentExportController<Concept, ConceptRepo> {

}
