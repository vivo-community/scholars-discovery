package edu.tamu.scholars.middleware.discovery.assembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.util.StreamUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.tamu.scholars.middleware.discovery.assembler.model.IndividualCollectionModel;
import edu.tamu.scholars.middleware.discovery.assembler.model.IndividualModel;
import edu.tamu.scholars.middleware.discovery.controller.IndividualSearchController;
import edu.tamu.scholars.middleware.discovery.model.Individual;

@Component
public class IndividualResourceAssembler extends RepresentationModelAssemblerSupport<Individual, IndividualModel> {

    public IndividualResourceAssembler() {
        super(IndividualSearchController.class, IndividualModel.class);
    }

    @Override
    public IndividualModel toModel(Individual document) {
        return new IndividualModel(document, Arrays.asList());
    }

    @Override
    public CollectionModel<IndividualModel> toCollectionModel(Iterable<? extends Individual> entities) {
        List<String> ids = new ArrayList<>();
        List<IndividualModel> content = StreamUtils.createStreamFromIterator(entities.iterator())
            .peek(entity -> ids.add(entity.getId()))
            .map(this::toModel)
            .collect(Collectors.toList());

        return new IndividualCollectionModel(content, Arrays.asList(), null);
    }

}
