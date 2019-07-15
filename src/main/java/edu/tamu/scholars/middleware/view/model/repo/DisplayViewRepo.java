package edu.tamu.scholars.middleware.view.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import edu.tamu.scholars.middleware.view.model.DisplayView;

@RepositoryRestResource
public interface DisplayViewRepo extends ViewRepo<DisplayView> {

    public Optional<DisplayView> findByTypesIn(List<String> types);

    public Optional<DisplayView> findByNameIn(List<String> names);

    @Override
    @RestResource(exported = true)
    public Optional<DisplayView> findByName(String name);
}
