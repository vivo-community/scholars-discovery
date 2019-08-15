package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.model.Process;
import edu.tamu.scholars.middleware.discovery.model.repo.ProcessRepo;

@RepositoryRestController
@RequestMapping("/processes")
public class ProcessExportController extends AbstractSolrDocumentExportController<Process, ProcessRepo> {

}
