package edu.tamu.scholars.middleware.discovery.assembler.model;

import org.springframework.core.ResolvableType;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.lang.Nullable;

public class IndividualCollectionModel extends CollectionModel<IndividualModel> {

    public IndividualCollectionModel(Iterable<IndividualModel> content, Iterable<Link> links, @Nullable ResolvableType fallbackType) {
        super(content, links, fallbackType);
    }

}
