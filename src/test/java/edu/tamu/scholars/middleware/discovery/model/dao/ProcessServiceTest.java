package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Process;
import edu.tamu.scholars.middleware.discovery.model.dao.ProcessService;
import edu.tamu.scholars.middleware.discovery.model.repo.ProcessRepo;

public class ProcessServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.generated.Process, Process, ProcessRepo, ProcessService> {

    @Value("classpath:mock/discovery/process")
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
    protected Class<?> getNestedDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Process.class;
    }

}
