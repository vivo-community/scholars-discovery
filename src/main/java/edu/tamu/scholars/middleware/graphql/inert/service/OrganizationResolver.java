package edu.tamu.scholars.middleware.graphql.inert.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
// import edu.tamu.scholars.middleware.graphql.model.Collection;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.tamu.scholars.middleware.graphql.inert.model.position.Organization;
import edu.tamu.scholars.middleware.graphql.inert.model.position.OrganizationStub;
import edu.tamu.scholars.middleware.graphql.inert.model.person.Position;
import java.util.List;
import java.util.Optional;

@GraphQLApi
@Service
public class OrganizationResolver {
  @Autowired
  private IndividualRepo repo;

  @Autowired
  private ObjectMapper mapper;

  @GraphQLQuery(name = "organization")
  // @formatter:off
  public Organization getById(
    @GraphQLArgument(name = "id") String id,
    @GraphQLEnvironment List<Field> fields
  ) {
    // @formatter:on


    Optional<Individual> organization = repo.findById(id);
    ObjectNode node = mapper.valueToTree(organization);
    System.out.println("**** NODE: " + node);
    return mapper.convertValue(node, Organization.class);
  }


  
  @GraphQLQuery
  public Organization[] organizations(@GraphQLContext Position position) {
     OrganizationStub[] organizations = position.getOrganization();
     Organization[] list = new Organization[organizations.length];

     for (int i = 0; i < organizations.length; i++) {  
       OrganizationStub orgStub = organizations[i];
       Optional<Individual> org = repo.findById(orgStub.getId());
       ObjectNode node = mapper.valueToTree(org);
       System.out.println("**** NODE: " + node);
       Organization real = mapper.convertValue(node, Organization.class);
       list[i] = real;
    }
    return list;
  }
  




}
