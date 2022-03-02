package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.control.trainAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

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
}
