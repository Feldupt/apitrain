package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.PlaceAssembler;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import org.apache.coyote.Response;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@ResponseBody
@RequestMapping(value = "/train/{trainId}/place", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value="/position/{position}")
    public ResponseEntity<?> findAllByTrainIdAndWindow(@PathVariable("trainId") String trainId, @PathVariable("position") boolean position) {
        Iterable<Place> allPlace = pr.findAllByTrainIdAndIsWindow(trainId, position);
        return ResponseEntity.ok(pa.toCollectionModel(allPlace));
    }

}
