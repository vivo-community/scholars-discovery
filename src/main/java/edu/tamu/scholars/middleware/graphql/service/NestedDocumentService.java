package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;

import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import graphql.language.Field;

public interface NestedDocumentService<ND extends AbstractNestedDocument> extends SolrDocumentRepo<ND> {

    public List<ND> findByIdIn(List<String> ids, List<Field> fields);

}
