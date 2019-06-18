package edu.tamu.scholars.middleware.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;

import edu.tamu.scholars.middleware.graphql.inputmodel.CreatePersonInput;

// stole from here:
// https://g00glen00b.be/graphql-mutations-spring/
@Component
public class MutationResolver implements GraphQLMutationResolver {

    @Autowired
    private PersonRepo personRepo;

    // TODO: seems kind of overly manual probably some
    // way to use reflection to go through all fields
    public Person createPerson(CreatePersonInput input) {
        Person person = new Person();
        person.setId(input.getId());
        person.setName(input.getName());

        //Person person = new Person(input);

        // how to do compound stuff ...
        // psuedo-code
        // for each input.education {
        //   person.addEducation("Ph.D. Doctor of Philosophy::edu000001")
        //   person.addEducationOrganization("University of Leuven::edu000001::org000001")
        //   person.addEducationStartDate("1988-01-01T00:00:00::edu000001")
        //   e.g. 
        //   person.addEducation("${ed.title}::${ed.id}")
        //   person.addEducationOrg("${ed.org.title}::${ed.id}::${ed.org.id}")
        //   person.addEducationStartDate("${ed.startDate}::${ed.id}")
        //   etc...
        //}
        return personRepo.save(person);
    }


    //public Person updatePerson(UpdatePersonInput input) {

    // NOTE: might need a  set attendedEvents ??  e.g.
    // public Person setEducations(UpdatePersonInput input) or
    // }
    // public Person setEducations(UpdatePersonEducationInput input) or
    // }

    // maybe this would be better to start with:
    /*
    educationAndTraining.items	educationAndTraining
    educationAndTrainingOrganization.items	organization
    educationAndTrainingMajorField.items	majorField
    educationAndTrainingDegreeAbbreviation.items	degreeAbbreviation
    educationAndTrainingStartDate.items	startDate
    educationAndTrainingEndDate.items	endDate
    */

    /*

  "educationAndTraining": [
    "Ph.D. Doctor of Philosophy::n3e016f20_1ca83fc1_730827c3"
  ],
  "educationAndTrainingOrganization": [
    "University of Leuven::n3e016f20_1ca83fc1_730827c3::n1ca83fc1"
  ],
  "educationAndTrainingMajorField": [
    "Zoology::n3e016f20_1ca83fc1_730827c3"
  ],
  "educationAndTrainingDegreeAbbreviation": [
    "Ph.D.::n3e016f20_1ca83fc1_730827c3"
  ],
  "educationAndTrainingEndDate": [
    "1988-01-01T00:00:00::n3e016f20_1ca83fc1_730827c3"
  ],

  */
    // for batch ...
    // there is a saveAll(people)
}
