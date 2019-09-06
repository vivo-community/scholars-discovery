package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.tamu.scholars.middleware.discovery.model.Individual;

@RepositoryRestResource
public interface IndividualRepo extends SolrDocumentRepo<Individual> {

}
