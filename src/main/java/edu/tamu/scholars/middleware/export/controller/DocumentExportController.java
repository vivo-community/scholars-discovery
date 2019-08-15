package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.model.Document;
import edu.tamu.scholars.middleware.discovery.model.repo.DocumentRepo;

@RepositoryRestController
@RequestMapping("/documents")
public class DocumentExportController extends AbstractSolrDocumentExportController<Document, DocumentRepo> {

}
