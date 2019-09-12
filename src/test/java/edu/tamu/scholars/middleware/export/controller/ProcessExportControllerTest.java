package edu.tamu.scholars.middleware.export.controller;

import edu.tamu.scholars.middleware.discovery.model.Process;

public class ProcessExportControllerTest extends AbstractSolrDocumentExportControllerTest<Process> {

    @Override
    protected Class<?> getType() {
        return Process.class;
    }

}
