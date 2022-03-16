package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.ConfirmationAssembler;
import fr.url.miage.apitrain.control.PlaceAssembler;
import fr.url.miage.apitrain.control.TrainAssembler;
import fr.url.miage.apitrain.entities.Confirmation;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@ResponseBody
@AllArgsConstructor
public class ReservationRepresentation {

    private final PlaceAssembler pa;
    private final PlaceResource pr;
    private final TrainResource tr;
    private final TrainAssembler ta;
    private final ConfirmationAssembler ca;


    //Aller simple + position
    @GetMapping(value="/{city1}/{city2}/aller/{day}/{time}/train/{trainId}/place/{position}")
    public ResponseEntity<?> getTrainBySimpleByDayByPosition(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("day") String day, @PathVariable("time") String time, @PathVariable("trainId") String trainId ,@PathVariable("position") String position) {
        boolean isWindow = position.equals("fenetre");


        System.out.println("Maposition: " + position + " " + isWindow);
        Optional<Train> allTrain = tr.findById(trainId);
        List<Place> allPlace = pr.findAllByTrainId(trainId);
        ArrayList<EntityModel<Place>> taList = new ArrayList<>();
        AtomicBoolean finalIsWindow = new AtomicBoolean(isWindow);
        allPlace.forEach((p->{
            if (position.equals("aleatoire"))
                finalIsWindow.set(Math.random() > 0.5);
            if(finalIsWindow.get() == p.isWindow())
            taList.add(pa.toModelPlace(p, allTrain.get(), city2, position));
        }));
        return ResponseEntity.ok(taList);
    }


    @GetMapping(value="/{city1}/{city2}/aller/{day}/{time}/train/{trainId}/place/{position}/{placeId}")
    public ResponseEntity<?> getOnePlaceByTrainById(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("day") String day, @PathVariable("time") String time, @PathVariable("trainId") String trainId ,@PathVariable("position") String position, @PathVariable("placeId") String placeId) {
        Optional<Train> allTrain = tr.findById(trainId);
        Place allPlace = pr.findPlaceByIdPlaceAndTrainId(placeId, trainId);

        Confirmation confirmation = new Confirmation(allTrain.get(), allPlace);

        return ResponseEntity.ok(ca.toModel(confirmation));
    }
}
