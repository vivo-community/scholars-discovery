package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Process;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Process"
})
public class ProcessControllerTest extends AbstractSolrDocumentControllerTest<Process> {

    @Override
    protected Class<?> getType() {
        return Process.class;
    }

}
