package edu.tamu.scholars.middleware.discovery.model.repo;

import edu.tamu.scholars.middleware.discovery.model.Process;

public class ProcessRepoTest extends AbstractSolrDocumentRepoTest<Process> {

    @Override
    protected Class<?> getType() {
        return Process.class;
    }

}
