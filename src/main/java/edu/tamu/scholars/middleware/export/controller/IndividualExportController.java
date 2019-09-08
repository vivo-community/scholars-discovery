package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/individuals")
public class IndividualExportController extends AbstractSolrDocumentExportController {

}
