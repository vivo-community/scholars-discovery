package edu.tamu.scholars.middleware.graphql.resolver;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.graphql.model.Document;
import edu.tamu.scholars.middleware.graphql.model.Person;
import edu.tamu.scholars.middleware.graphql.service.DocumentService;
import edu.tamu.scholars.middleware.graphql.service.PersonService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class EnhancedPersonResolver {

    private final static int MAX_DOCUMENT_BATCH_SIZE = 500;

    @Autowired
    private PersonService personService;

    @Autowired
    private DocumentService documentService;

    @GraphQLQuery(name = "person")
    public EnhancedPerson getById(@GraphQLArgument(name = "id") String id) {
        Person person = personService.getById(id);
        EnhancedPerson enhancedPerson = new EnhancedPerson();
        BeanUtils.copyProperties(person, enhancedPerson);
        return enhancedPerson;
    }

    // NOTE: This concrete class extending the generated nested Person class is required
    // to generate the GraphQL schema in which to support return type with a Person and 
    // a list of full publications. Also, undesired is the additional list of publications.
    // Unfortunately, there is no way to override the existing property accessor methods 
    // with a different generic for the return list.
    // To remedy this, we could annotate the flatten Solr document models with information
    // on how to generate nested documents with complete relational nested documents. There
    // will have to be a naming convention used.
    // I would also like to have annotations on the flatten Solr document models specify
    // the variable name more appropriate than label of the partial nested objects.
    @GraphQLType(name = "EnhancedPerson")
    public class EnhancedPerson extends Person {

        private static final long serialVersionUID = -2574871417252355891L;

        public List<Document> getSelectedPublications() {
          return documentService.findBySyncIds(this.getId());
        }

    }

}
