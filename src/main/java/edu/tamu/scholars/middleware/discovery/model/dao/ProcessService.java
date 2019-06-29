package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Process;
import edu.tamu.scholars.middleware.discovery.model.repo.ProcessRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class ProcessService extends AbstractNestedDocumentService<Process, edu.tamu.scholars.middleware.discovery.model.Process, ProcessRepo> {

    @Override
    @GraphQLQuery(name = "processes")
    public Iterable<Process> findAll() {
        return super.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Process.class;
    }

}
