package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.tamu.scholars.middleware.discovery.model.Person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;


@RepositoryRestResource
public interface PersonRepo extends SolrDocumentRepo<Person> {

    @Query("*?0*")
    public Page<Person> findByCustomQuery(String query, Pageable pageable);
  
  
}
