package fr.url.miage.apitrain.control;

import fr.url.miage.apitrain.boundary.PaiementRepresentation;
import fr.url.miage.apitrain.boundary.TrainRepresentation;
import fr.url.miage.apitrain.entities.ConfirmationBack;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConfirmationBackAssembler implements RepresentationModelAssembler<ConfirmationBack, EntityModel<ConfirmationBack>> {


    @Override
    public EntityModel<ConfirmationBack> toModel(ConfirmationBack confirmation) {

        return EntityModel.of(
                confirmation,
                linkTo(methodOn(TrainRepresentation.class)
                        .getOneTrainById(confirmation.getTrain().getId())).withRel("train"),
                linkTo(methodOn(PaiementRepresentation.class)
                        .processPayment(confirmation.getPlaceId(), confirmation.getPlaceIdBack())).withRel("paiement"));

    }


}
