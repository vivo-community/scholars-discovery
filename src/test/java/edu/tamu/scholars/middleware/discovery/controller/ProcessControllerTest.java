package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Process;

public class ProcessControllerTest extends AbstractSolrDocumentControllerTest<Process> {

    @Value("classpath:mock/discovery/processes")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Process.class;
    }

    @Override
    protected String getPath() {
        return "/individual";
    }

}
