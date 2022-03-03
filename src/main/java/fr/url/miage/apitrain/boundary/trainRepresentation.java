package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.trainAssembler;
import fr.url.miage.apitrain.entities.Train;
import fr.url.miage.apitrain.entities.trainInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

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

    @PostMapping
    @Transactional
    public ResponseEntity<?> saveTrain(@RequestBody @Valid trainInput train){
        Train trainToSave = new Train(
                UUID.randomUUID().toString(),
                train.getName(),
                train.getFelzer(),
                train.getNumberOfPlaceFirstClass(),
                train.getNumberOfPlaceSecondClass(),
                train.isBar()
        );

        Train trainSaved = tr.save(trainToSave);
        URI location = linkTo(trainRepresentation.class).slash(trainSaved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
