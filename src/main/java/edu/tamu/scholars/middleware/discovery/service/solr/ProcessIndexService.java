package edu.tamu.scholars.middleware.discovery.service;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Process;

@Service
public class ProcessIndexService extends AbstractSolrIndexService<Process> {

    @Override
    public Class<?> type() {
        return Process.class;
    }

}
