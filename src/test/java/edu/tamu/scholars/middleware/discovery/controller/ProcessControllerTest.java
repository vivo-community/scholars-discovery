package edu.tamu.scholars.middleware.discovery.controller;

import edu.tamu.scholars.middleware.discovery.model.Process;

public class ProcessControllerTest extends AbstractSolrDocumentControllerTest<Process> {

    @Override
    protected Class<?> getType() {
        return Process.class;
    }

}
