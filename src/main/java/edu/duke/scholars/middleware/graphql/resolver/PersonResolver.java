package edu.duke.scholars.middleware.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.tamu.scholars.middleware.discovery.model.Document;
import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.DocumentRepo;

@Component
public class PersonResolver implements GraphQLResolver<Person> {

  @Autowired
  private DocumentRepo documentRepo;

  public Iterable<Document> documents(Person person) {
    return documentRepo.findAllByAuthors(person.getName());
  }

}
