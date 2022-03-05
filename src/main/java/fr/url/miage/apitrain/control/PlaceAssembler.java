package fr.url.miage.apitrain.control;

import fr.url.miage.apitrain.boundary.PlaceRepresentation;
import fr.url.miage.apitrain.boundary.TrainRepresentation;
import fr.url.miage.apitrain.entities.Place;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlaceAssembler implements RepresentationModelAssembler<Place, EntityModel<Place>> {

    @Override
    public EntityModel<Place> toModel(Place place) {
        String trainId = place.getTrain().getId();
      return EntityModel.of(place,
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("places"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(trainId)).withRel("train"));
    }

    @Override
    public CollectionModel<EntityModel<Place>> toCollectionModel(Iterable<? extends Place> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }


}
