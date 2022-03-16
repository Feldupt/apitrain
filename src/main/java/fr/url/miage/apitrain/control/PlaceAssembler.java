package fr.url.miage.apitrain.control;

import fr.url.miage.apitrain.boundary.PlaceRepresentation;
import fr.url.miage.apitrain.boundary.ReservationRepresentation;
import fr.url.miage.apitrain.boundary.TrainRepresentation;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlaceAssembler implements RepresentationModelAssembler<Place, EntityModel<Place>> {

    @Override
    public EntityModel<Place> toModel(Place place) {
        String trainId = place.getTrain().getId();

      return EntityModel.of(place,
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("collection"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(trainId)).withRel("train"));

    }

    public EntityModel<Place> toModelPlaceBack(Place place, Train train, String city2, String position, String dayBack, String timeBack) {
        String trainId = place.getTrain().getId();

        return EntityModel.of(place,
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("collection"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(trainId)).withRel("train"),
                linkTo(methodOn(ReservationRepresentation.class)
                        .getOnePlaceByTrainById(train.getStartCity(), city2, place.getIdPlace(), dayBack, timeBack)).withRel("choisir-retour"));

    }

    public EntityModel<Place> toModelPlaceBackBack(Place place, Train train, String city2, String position, String dayBack, String timeBack, String placeId) {
        String trainId = place.getTrain().getId();

        return EntityModel.of(place,
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("collection"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(trainId)).withRel("train"),
                linkTo(methodOn(ReservationRepresentation.class)
                        .getOnePlaceByTrainByIdBackBack(train.getStartCity(), city2, placeId, place.getIdPlace())).withRel("choisir"));

    }

    public EntityModel<Place> toModelPlace(Place place, Train train, String city2, String position) {
        String trainId = place.getTrain().getId();

        return EntityModel.of(place,
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("collection"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(trainId)).withRel("train"),
                linkTo(methodOn(ReservationRepresentation.class)
                        .getOnePlaceByTrainById(train.getStartCity(), city2, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(train.getDate()), DateTimeFormatter.ofPattern("HH").format(train.getDate()), train.getId(), position, place.getIdPlace())).withRel("choisir"));

    }

    @Override
    public CollectionModel<EntityModel<Place>> toCollectionModel(Iterable<? extends Place> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }


}
