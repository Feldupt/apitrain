package fr.url.miage.apitrain.control;

import fr.url.miage.apitrain.boundary.PlaceRepresentation;
import fr.url.miage.apitrain.boundary.TrainRepresentation;
import fr.url.miage.apitrain.entities.Train;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TrainAssembler implements RepresentationModelAssembler<Train,EntityModel<Train>> {

    @Override
    public EntityModel<Train> toModel(Train train) {
        String trainId = train.getId();
        return EntityModel.of(train,
                linkTo(methodOn(TrainRepresentation.class)
                        .getAllTrain()).withRel("collection"),
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("places"));
    }

    @Override
    public CollectionModel<EntityModel<Train>> toCollectionModel(Iterable<? extends Train> entities) {
        List<EntityModel<Train>> trainModel = StreamSupport
                .stream(entities.spliterator(), false)
                .map(this::toModel)
                .toList();
        return CollectionModel.of(trainModel,
                linkTo(methodOn(TrainRepresentation.class)
                        .getAllTrain()).withSelfRel());
    }
}
