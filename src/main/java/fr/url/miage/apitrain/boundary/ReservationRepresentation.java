package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.ConfirmationAssembler;
import fr.url.miage.apitrain.control.ConfirmationBackAssembler;
import fr.url.miage.apitrain.control.PlaceAssembler;
import fr.url.miage.apitrain.control.TrainAssembler;
import fr.url.miage.apitrain.entities.Confirmation;
import fr.url.miage.apitrain.entities.ConfirmationBack;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final ConfirmationBackAssembler cba;

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

    @GetMapping(value="/{city1}/{city2}/aller/{day}/{time}/{dayBack}/{timeBack}/train/{trainId}/place/{position}")
    public ResponseEntity<?> getTrainBySimpleByDayByPositionBack(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("day") String day, @PathVariable("time") String time, @PathVariable("trainId") String trainId ,@PathVariable("position") String position,@PathVariable("timeBack") String timeBack,@PathVariable("dayBack") String dayBack) {
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
                taList.add(pa.toModelPlaceBack(p, allTrain.get(), city2, position, dayBack, timeBack));
        }));
        return ResponseEntity.ok(taList);
    }

    @GetMapping(value="/{city1}/{city2}/place-aller/{placeId}/{dayBack}/{timeBack}/train/{trainId}/place/{position}")
    public ResponseEntity<?> getTrainBySimpleByDayByPositionBackBack(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("dayBack") String dayBack, @PathVariable("timeBack") String timeBack, @PathVariable("trainId") String trainId ,@PathVariable("position") String position,@PathVariable("placeId") String placeId) {
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
                taList.add(pa.toModelPlaceBackBack(p, allTrain.get(), city2, position, dayBack, timeBack, placeId));
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

    @GetMapping(value="/{city1}/{city2}/place-aller/{placeId}/place-retour/{placeIdBack}")
    public ResponseEntity<?> getOnePlaceByTrainByIdBackBack(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("placeId") String placeId, @PathVariable("placeIdBack") String placeIdBack) {

        Place placeAller = pr.findPlaceByIdPlace(placeId);
        Place placeRetour = pr.findPlaceByIdPlace(placeIdBack);

        ConfirmationBack confirmation = new ConfirmationBack(placeAller, placeRetour);

        return ResponseEntity.ok(cba.toModel(confirmation));
    }

    @GetMapping(value="/{city1}/{city2}/place-aller/{placeId}/{dayBack}/{timeBack}")
    public ResponseEntity<?> getOnePlaceByTrainById(@PathVariable("city1") String city1, @PathVariable("city2") String city2, @PathVariable("placeId") String placeId, @PathVariable("timeBack") String timeBack,@PathVariable("dayBack") String dayBack) {
        String[] tmpDate = dayBack.split("-");
        LocalDate date2 = LocalDate.from(LocalDate.of(Integer.parseInt(tmpDate[2]), Integer.parseInt(tmpDate[1]), Integer.parseInt(tmpDate[0])));
        LocalTime tst = LocalTime.parse(timeBack+":00:00");
        LocalDateTime newDate = LocalDateTime.of(date2, tst);
        System.out.println(newDate);
        //yyyy-MM-dd
        Set<String> journey = new HashSet<>();
        journey.add(city1);

        List<Train> list = tr.findAllByStartCityAndJourneyInAndDateIsGreaterThanEqual(city2, journey, newDate);
        ArrayList<EntityModel<Train>> taList = new ArrayList<>();
        list.forEach((t->{
            taList.add(ta.toModelGetOneTrainByCityBackBack(t, city1, placeId));
        }));
        return ResponseEntity.ok(taList);

    }

}
