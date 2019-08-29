package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.assembler.RelationshipResourceAssembler;
import edu.tamu.scholars.middleware.discovery.model.Relationship;
import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;
import edu.tamu.scholars.middleware.discovery.resource.RelationshipResource;

@RepositoryRestController
@RequestMapping("/relationships")
public class RelationshipController extends AbstractSolrDocumentController<Relationship, RelationshipRepo, RelationshipResource, RelationshipResourceAssembler> {

}
