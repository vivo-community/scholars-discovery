package edu.tamu.scholars.middleware.graphql.resolver;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private PersonService personService;

    @Autowired
    private DocumentService documentService;

    @GraphQLQuery(name = "person")
    public EnhancedPerson getById(@GraphQLArgument(name = "id") String id) {
        Person person = personService.getById(id);
        List<String> ids = person.getPublications().stream().map(document -> document.getId()).collect(Collectors.toList());
        List<Document> selectedPublications = documentService.findByIdIn(ids);
        EnhancedPerson enhancedPerson = new EnhancedPerson();
        enhancedPerson.setSelectedPublications(selectedPublications);
        BeanUtils.copyProperties(person, enhancedPerson);
        return enhancedPerson;
    }

    @GraphQLType(name = "EnhancedPerson")
    public class EnhancedPerson extends Person {

        private static final long serialVersionUID = -2574871417252355891L;

        private List<Document> selectedPublications;

        public List<Document> getSelectedPublications() {
            return selectedPublications;
        }

        public void setSelectedPublications(List<Document> selectedPublications) {
            this.selectedPublications = selectedPublications;
        }

    }

}
