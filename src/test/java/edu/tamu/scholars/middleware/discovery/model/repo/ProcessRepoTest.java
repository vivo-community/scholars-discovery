package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Process;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Process"
})
public class ProcessRepoTest extends AbstractSolrDocumentRepoTest<Process> {

    @Override
    protected Class<?> getType() {
        return Process.class;
    }

}
