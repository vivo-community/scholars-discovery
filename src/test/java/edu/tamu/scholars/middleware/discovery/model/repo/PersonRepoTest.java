package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Person;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Person"
})
public class PersonRepoTest extends AbstractSolrDocumentRepoTest<Person> {

    @Override
    protected Class<?> getType() {
        return Person.class;
    }

}
