package fr.url.miage.apitrain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.SecondaryTable;

@Getter
@Setter

public class Confirmation {

    private String trainId;
    private Train train;
    private String placeId;
    private Place place;


    public Confirmation(Train train, Place place) {
        this.train = train;
        this.place = place;
        trainId = train.getId();
        placeId = place.getIdPlace();
    }
}
