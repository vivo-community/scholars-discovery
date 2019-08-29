package edu.tamu.scholars.middleware.export.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;

@RepositoryRestController
@RequestMapping("/persons")
public class PersonExportController extends AbstractSolrDocumentExportController<Person, PersonRepo> {

}
