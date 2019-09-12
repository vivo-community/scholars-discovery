package edu.tamu.scholars.middleware.discovery.model.repo;

import edu.tamu.scholars.middleware.discovery.model.Person;

public class PersonRepoTest extends AbstractSolrDocumentRepoTest<Person> {

    @Override
    protected Class<?> getType() {
        return Person.class;
    }

}
