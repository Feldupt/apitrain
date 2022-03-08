package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.PlaceAssembler;
import fr.url.miage.apitrain.control.TrainAssembler;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import fr.url.miage.apitrain.entities.TrainInput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
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
@AllArgsConstructor
public class ReservationRepresentation {

    private final PlaceAssembler pa;
    private final PlaceResource pr;
    private final TrainResource tr;
    private final TrainAssembler ta;


    //Aller simple + position
    @GetMapping(value="/{city1}/{city2}/aller/{day}/{time}/train/{trainId}/place/{position}")
    public ResponseEntity<?> getTrainBySimpleByDayByPosition(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("day") String day, @PathVariable("time") String time, @PathVariable("trainId") String trainId ,@PathVariable("position") String position) {
        boolean isWindow = false;
        if (position.equals("fenetre"))
            isWindow = true;
        if (position.equals("aleatoire"))
            isWindow = Math.random() > 0.5;
        Optional<Train> allTrain = tr.findById(trainId);
        List<Place> allPlace = pr.findAllByTrainId(trainId);
        ArrayList<EntityModel<Place>> taList = new ArrayList<>();
        allPlace.forEach((p->{
            taList.add(pa.toModelPlace(p, allTrain.get(), city2, position));
        }));
        return ResponseEntity.ok(taList);
    }


    @GetMapping(value="/{city1}/{city2}/aller/{day}/{time}/train/{trainId}/place/{position}/{placeId}")
    public ResponseEntity<?> getOnePlaceByTrainById(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("day") String day, @PathVariable("time") String time, @PathVariable("trainId") String trainId ,@PathVariable("position") String position, @PathVariable("placeId") String placeId) {
        Optional<Train> allTrain = tr.findById(trainId);
        Place allPlace = (Place) pr.findPlaceByIdPlaceAndTrainId(placeId, trainId);
        return ResponseEntity.ok(pa.toModelPlace(allPlace, allTrain.get(), city2, position));
    }
}
