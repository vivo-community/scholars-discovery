package edu.duke.scholars.middleware.graphql.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;
import edu.tamu.scholars.middleware.graphql.inputmodel.Paging;

@Component
public class PersonQuery implements GraphQLQueryResolver {

  @Autowired
  private PersonRepo personRepo;

  public List<Person> people(String query, Paging paging) {

    Page<Person> personPage;

    Pageable pageable = PageRequest.of(paging.getPage(),paging.getPageSize());
    if (query != null) {
      personPage = personRepo.findByCustomQuery(query, pageable);
    } else {
      personPage = personRepo.findAll(pageable);
    }
    return personPage.getContent();
  }

}

