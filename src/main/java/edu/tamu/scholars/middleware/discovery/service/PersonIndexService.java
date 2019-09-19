package edu.tamu.scholars.middleware.discovery.service;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Person;

@Service
public class PersonIndexService extends AbstractSolrIndexService<Person> {

    @Override
    public Class<?> type() {
        return Person.class;
    }

}
