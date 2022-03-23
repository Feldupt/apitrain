package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.entities.Place;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> processPayment(@PathVariable("placeId") String placeId)
    {
        Place place = pr.getById(placeId);
        if(place.isOccupied()){
            return ResponseEntity.badRequest().build();
        }
        place.setOccupied(true);
        pr.save(place);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/{placeIdAller}/{placeIdRetour}")
    public ResponseEntity<?> processPayment(@PathVariable("placeIdAller") String placeIdAller, @PathVariable("placeIdRetour") String placeIdRetour)
    {
        Place placeAller = pr.getById(placeIdAller);
        Place placeRetour = pr.getById(placeIdRetour);

        if(placeAller.isOccupied() || placeRetour.isOccupied()){
            return ResponseEntity.badRequest().build();
        }
        placeAller.setOccupied(true);
        pr.save(placeAller);
        placeRetour.setOccupied(true);
        pr.save(placeRetour);

        return ResponseEntity.ok().build();
    }


}
