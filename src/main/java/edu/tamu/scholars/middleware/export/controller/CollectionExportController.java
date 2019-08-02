package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.model.Collection;
import edu.tamu.scholars.middleware.discovery.model.repo.CollectionRepo;

@BasePathAwareController
@RequestMapping("/collections")
public class CollectionExportController extends AbstractSolrDocumentExportController<Collection, CollectionRepo> {

}
