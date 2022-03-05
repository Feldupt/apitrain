package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.trainAssembler;
import fr.url.miage.apitrain.entities.Train;
import fr.url.miage.apitrain.entities.trainInput;
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
public class trainRepresentation{

    private final trainResource tr;
    private final trainAssembler ta;

    // grâce au constructeur, Spring injecte une instance de ir
    public trainRepresentation(trainResource tr, trainAssembler ta) {
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
        return Optional.ofNullable(tr.findByStartCityAndJourneyIn(city1, journey))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(ta.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

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
        /*
        for (int i = date2.getHour() + 1; i <= 23; i++) {
            LocalDateTime date3 = LocalDate.now().atTime(Integer.parseInt(String.valueOf(i)), 0);
            Optional<Train> trainList = tr.findByStartCityAndJourneyInAndDate(city1, journey, date3);
            totalListTrain.add(trainList);
        }
         */

        List<Train> list = tr.findAllByStartCityAndJourneyInAndDateIsGreaterThanEqual(city1, journey, newDate);
        ArrayList<EntityModel<Train>> taList = new ArrayList<>();
        list.forEach((t->{
        taList.add(ta.toModel(t));
}));
        return ResponseEntity.ok(taList);
                /*
        return Optional.ofNullable(tr.findByStartCityAndJourneyInAndDateIsGreaterThanEqual(city1, journey, newDate))
                .filter(Optional::isPresent)
                .map(i -> {
                    ResponseEntity.ok(ta.toModel(i.get()));
                } )
                .orElse(ResponseEntity.notFound().build());


                 */

    }

    @GetMapping(value="/{city1}/{city2}/aller/{day}/{time}/couloir")
    public ResponseEntity<?> getTrainBySimpleByDayByPosition(@PathVariable("city1") String city1, @PathVariable("city2") String city2,@PathVariable("day") String day, @PathVariable("time") String time)
    {
        String[] tmpDate = day.split("-");
        LocalDate date2 = LocalDate.from(LocalDate.of(Integer.parseInt(tmpDate[2]), Integer.parseInt(tmpDate[1]), Integer.parseInt(tmpDate[0])));
        LocalTime tst = LocalTime.parse(time+":00:00");
        LocalDateTime newDate = LocalDateTime.of(date2, tst);
        System.out.println(newDate);
        //yyyy-MM-dd
        Set<String> journey = new HashSet<>();
        journey.add(city2);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/{city1}/{city2}/aller-retour/{day}/{time}/{dayBack}/{timeBack}")
    public ResponseEntity<?> getTrainByReturnByDay(@PathVariable("city1") String city1, @PathVariable("city2") String city2,@PathVariable("day") String dayGo, @PathVariable("time") String timeGo, @PathVariable("dayBack") String dayBack, @PathVariable("timeBack") String timeBack)
    {
        String[] tmpDate = dayGo.split("-");
        LocalDate date2 = LocalDate.from(LocalDate.of(Integer.parseInt(tmpDate[2]), Integer.parseInt(tmpDate[1]), Integer.parseInt(tmpDate[0])));
        LocalTime tst = LocalTime.parse(timeGo+":00:00");
        String[] tmpDateBack = dayBack.split("-");
        LocalDate date2Back = LocalDate.from(LocalDate.of(Integer.parseInt(tmpDateBack[2]), Integer.parseInt(tmpDateBack[1]), Integer.parseInt(tmpDateBack[0])));
        LocalTime tstBack = LocalTime.parse(timeBack+":00:00");
        LocalDateTime newDate = LocalDateTime.of(date2Back, tstBack);
        System.out.println(newDate);
        //yyyy-MM-dd
        Set<String> journey = new HashSet<>();
        journey.add(city1);

/*
        return Optional.ofNullable(tr.findByStartCityAndJourneyInAndDateIsGreaterThanEqual(city1, journey, newDate))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(ta.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());



 */
        return ResponseEntity.ok().build();
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> saveTrain(@RequestBody @Valid trainInput train){
        Train trainToSave = new Train(
                UUID.randomUUID().toString(),
                train.getName(),
                train.getJourney(),
                train.getStartCity(),
                train.getNumberOfPlaceFirstClass(),
                train.getNumberOfPlaceSecondClass(),
                train.isBar(),
                train.getDate()
        );

        Train trainSaved = tr.save(trainToSave);
        URI location = linkTo(trainRepresentation.class).slash(trainSaved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    //aller -> aller simple
    //aller-retour -> aller-retour



}
