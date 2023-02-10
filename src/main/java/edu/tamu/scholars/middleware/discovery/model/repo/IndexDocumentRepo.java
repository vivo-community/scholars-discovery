package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.custom.IndexDocumentRepoCustom;

@NoRepositoryBean
public interface IndexDocumentRepo<D extends AbstractIndexDocument> extends PagingAndSortingRepository<D, String>, IndexDocumentRepoCustom<D> {

    @Override
    @RestResource(exported = false)
    public <S extends D> S save(S document);

    @Override
    @RestResource(exported = false)
    public void delete(D document);

}
