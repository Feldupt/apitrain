package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.TrainAssembler;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import fr.url.miage.apitrain.entities.TrainInput;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@ResponseBody
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaiementRepresentation {

    private final PlaceResource pr;

    public PaiementRepresentation(PlaceResource pr) {
        this.pr = pr;
    }


    @GetMapping(value="/{placeId}")
    public ResponseEntity<?> paymentSuccessForPlace(@PathVariable("placeId") String placeId)
    {
        Place place = pr.getById(placeId);
        if(place.isOccupied()){
            return ResponseEntity.badRequest().build();
        }
        place.setOccupied(true);
        pr.save(place);
        return ResponseEntity.ok().build();
    }



}
