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
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.tamu.scholars.middleware.graphql.inert.model.person.Position;

import java.util.List;
import java.util.Optional;

@GraphQLApi
@Service
public class PositionResolver {
  @Autowired
  private IndividualRepo repo;

  @Autowired
  private ObjectMapper mapper;

  @GraphQLQuery(name = "position")
  // @formatter:off
  public Position getById(
    @GraphQLArgument(name = "id") String id,
    @GraphQLEnvironment List<Field> fields
  ) {
    // @formatter:on


    Optional<Individual> position = repo.findById(id);
    ObjectNode node = mapper.valueToTree(position);
    System.out.println("**** NODE: " + node);
    return mapper.convertValue(node, Position.class);
  }

}
