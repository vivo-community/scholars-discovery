package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.dao.PersonService;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;

public class PersonServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.generated.Person, Person, PersonRepo, PersonService> {

    @Value("classpath:mock/discovery/person")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Person.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Person.class;
    }

}
