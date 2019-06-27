package edu.tamu.scholars.middleware.discovery.model.doa;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;

@Service
public class PersonService extends AbstractSolrDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.person.Person, Person, PersonRepo> {

    @Override
    protected Class<?> getNestedDocumentClass() {
        return edu.tamu.scholars.middleware.discovery.model.generated.person.Person.class;
    }

}
