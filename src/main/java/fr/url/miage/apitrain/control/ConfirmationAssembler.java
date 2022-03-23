package fr.url.miage.apitrain.control;

import fr.url.miage.apitrain.boundary.PaiementRepresentation;
import fr.url.miage.apitrain.boundary.TrainRepresentation;
import fr.url.miage.apitrain.entities.Confirmation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

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
                .processPayment(confirmation.getPlaceId())).withRel("paiement"));

    }

}
