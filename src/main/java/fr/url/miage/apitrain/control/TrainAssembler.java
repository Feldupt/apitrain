package fr.url.miage.apitrain.control;

import fr.url.miage.apitrain.boundary.PlaceRepresentation;
import fr.url.miage.apitrain.boundary.TrainRepresentation;
import fr.url.miage.apitrain.entities.Train;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TrainAssembler implements RepresentationModelAssembler<Train,EntityModel<Train>> {
//self : renvoie lui même
    @Override
    public EntityModel<Train> toModel(Train train) {
        String trainId = train.getId();
        return EntityModel.of(train,
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(train.getId())).withRel("self"),
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("places"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getAllTrain()).withRel("collection"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getTrainBySimpleByDay(train.getStartCity(), train.getJourney().stream().toList().get(0), train.getDate().toString(), train.getDate().toString())).withRel("aller"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getAllTrain()).withRel("aller-retour"));

    }


    public EntityModel<Train> toModelGetOneTrainByCity(Train train, String city2) {
        String trainId = train.getId();
        return EntityModel.of(train,
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(train.getId())).withRel("self"),
                linkTo(methodOn(PlaceRepresentation.class)
                        .getAllPlaceByTrain(trainId)).withRel("places"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getAllTrain()).withRel("collection"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getTrainBySimpleByDay(train.getStartCity(), city2, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(train.getDate()),DateTimeFormatter.ofPattern("HH").format(train.getDate()))).withRel("aller"),
                linkTo(methodOn(TrainRepresentation.class)
                        .getAllTrain()).withRel("aller-retour"));

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
