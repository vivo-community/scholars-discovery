package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.model.Relationship;
import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;

@RepositoryRestController
@RequestMapping("/relationships")
public class RelationshipExportController extends AbstractSolrDocumentExportController<Relationship, RelationshipRepo> {

}
