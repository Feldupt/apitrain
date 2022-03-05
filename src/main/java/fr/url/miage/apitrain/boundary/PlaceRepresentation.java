package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.PlaceAssembler;
import fr.url.miage.apitrain.entities.Place;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping(value = "/{trainId}/place", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlaceRepresentation {

    private final PlaceResource pr;
    private final PlaceAssembler pa;

    public PlaceRepresentation(PlaceResource pr, PlaceAssembler pa) {
        this.pr = pr;
        this.pa = pa;
    }

    @GetMapping()
    public ResponseEntity<?> getAllPlaceByTrain(@PathVariable("trainId") String trainId) {
        Iterable<Place> allPlace = pr.findAllByTrainId(trainId);
        return ResponseEntity.ok(pa.toCollectionModel(allPlace));
    }


}
