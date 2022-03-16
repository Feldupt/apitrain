package fr.url.miage.apitrain.control;

import fr.url.miage.apitrain.boundary.PaiementRepresentation;
import fr.url.miage.apitrain.boundary.PlaceRepresentation;
import fr.url.miage.apitrain.boundary.ReservationRepresentation;
import fr.url.miage.apitrain.boundary.TrainRepresentation;
import fr.url.miage.apitrain.entities.Confirmation;
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
public class ConfirmationAssembler implements RepresentationModelAssembler<Confirmation, EntityModel<Confirmation>> {

    @Override
    public EntityModel<Confirmation> toModel(Confirmation confirmation) {

      return EntityModel.of(
              confirmation,
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(confirmation.getTrain().getId())).withRel("train"),
        linkTo(methodOn(PaiementRepresentation.class)
                .paymentSuccessForPlace(confirmation.getPlaceId())).withRel("paiement"));

    }


}
