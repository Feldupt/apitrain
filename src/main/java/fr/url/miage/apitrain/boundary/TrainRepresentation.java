package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.TrainAssembler;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import fr.url.miage.apitrain.entities.TrainInput;
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
@RequestMapping(value = "/train", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainRepresentation {

    private final PlaceResource pr;
    private final TrainResource tr;
    private final TrainAssembler ta;

    // grâce au constructeur, Spring injecte une instance de ir
    public TrainRepresentation(PlaceResource pr, TrainResource tr, TrainAssembler ta) {
        this.pr = pr;
        this.tr = tr;
        this.ta = ta;
    }

    @GetMapping
    public ResponseEntity<?> getAllTrain(){
        return ResponseEntity.ok(ta.toCollectionModel(tr.findAll()));
    }

    @GetMapping(value="/{trainId}")
    public ResponseEntity<?> getOneTrainById(@PathVariable("trainId") String trainId)
    {
        return Optional.ofNullable(tr.findById(trainId))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(ta.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value="/{city1}/{city2}")
    public ResponseEntity<?> getOneTrainByCity(@PathVariable("city1") String city1, @PathVariable("city2") String city2)
    {
        Set<String> journey = new HashSet<>();
        journey.add(city2);
                List<Train> list = tr.findByStartCityAndJourneyIn(city1, journey);
                ArrayList<EntityModel<Train>> taList = new ArrayList<>();
                list.forEach((t->{
                            taList.add(ta.toModelGetOneTrainByCity(t, city2));
    }));
        return ResponseEntity.ok(taList);

    }

    //Aller simple
    @GetMapping(value="/{city1}/{city2}/aller/{day}/{time}")
    public ResponseEntity<List<?>> getTrainBySimpleByDay(@PathVariable("city1") String city1, @PathVariable("city2") String city2,@PathVariable("day") String day, @PathVariable("time") String time)
    {
        String[] tmpDate = day.split("-");
        LocalDate date2 = LocalDate.from(LocalDate.of(Integer.parseInt(tmpDate[2]), Integer.parseInt(tmpDate[1]), Integer.parseInt(tmpDate[0])));
        LocalTime tst = LocalTime.parse(time+":00:00");
        LocalDateTime newDate = LocalDateTime.of(date2, tst);
        System.out.println(newDate);
        //yyyy-MM-dd
        Set<String> journey = new HashSet<>();
        journey.add(city2);

        List<Train> list = tr.findAllByStartCityAndJourneyInAndDateIsGreaterThanEqual(city1, journey, newDate);
        ArrayList<EntityModel<Train>> taList = new ArrayList<>();
        list.forEach((t->{
        taList.add(ta.toModelGetOneTrainByCity(t, city2));
}));
        return ResponseEntity.ok(taList);

    }

    //Aller retour
    @GetMapping(value="/{city1}/{city2}/aller-retour/{day}/{time}/{dayBack}/{timeBack}")
    public ResponseEntity<List<?>> getTrainByBackByDay(@PathVariable("city1") String city1, @PathVariable("city2") String city2,@PathVariable("day") String day, @PathVariable("time") String time, @PathVariable("dayBack") String dayBack, @PathVariable("timeBack") String timeBack)
    {
        String[] tmpDate = day.split("-");
        LocalDate date2 = LocalDate.from(LocalDate.of(Integer.parseInt(tmpDate[2]), Integer.parseInt(tmpDate[1]), Integer.parseInt(tmpDate[0])));
        LocalTime tst = LocalTime.parse(time+":00:00");
        LocalDateTime newDate = LocalDateTime.of(date2, tst);
        System.out.println(newDate);
        //yyyy-MM-dd
        Set<String> journey = new HashSet<>();
        journey.add(city2);

        List<Train> list = tr.findAllByStartCityAndJourneyInAndDateIsGreaterThanEqual(city1, journey, newDate);
        ArrayList<EntityModel<Train>> taList = new ArrayList<>();
        list.forEach((t->{
            taList.add(ta.toModelGetOneTrainByCityBack(t, city2, dayBack, timeBack));
        }));
        return ResponseEntity.ok(taList);

    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> saveTrain(@RequestBody @Valid TrainInput train){
        Train trainToSave = new Train(
                UUID.randomUUID().toString(),
                train.getName(),
                train.getJourney(),
                train.getStartCity(),
                train.getFirstClassPlace(),
                train.getSecondClassPlace(),
                train.getNumberOfPlaceFirstClass(),
                train.getNumberOfPlaceSecondClass(),
                train.isBar(),
                train.getDate()
        );

        Train trainSaved = tr.save(trainToSave);
        for (int i = 0; i < trainSaved.getNumberOfPlaceFirstClass() ; i++) {
            pr.save(new Place(UUID.randomUUID().toString(), i % 2 == 0,false ,trainSaved, 110+new Random().nextInt(20)));
        }
        for (int i = 0; i < trainSaved.getNumberOfPlaceSecondClass() ; i++) {
            pr.save(new Place(UUID.randomUUID().toString(), i % 2 == 0,false ,trainSaved, 10+new Random().nextInt(20)));
        }
        URI location = linkTo(TrainRepresentation.class).slash(trainSaved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    //aller -> aller simple
    //aller-retour -> aller-retour



}
