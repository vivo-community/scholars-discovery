package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.assembler.ProcessResourceAssembler;
import edu.tamu.scholars.middleware.discovery.model.Process;
import edu.tamu.scholars.middleware.discovery.model.repo.ProcessRepo;
import edu.tamu.scholars.middleware.discovery.resource.ProcessResource;

@RepositoryRestController
@RequestMapping("/processes")
public class ProcessController extends AbstractSolrDocumentController<Process, ProcessRepo, ProcessResource, ProcessResourceAssembler> {

}
