package fr.url.miage.apitrain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ConfirmationBack {

    private String trainId;
    private Train train;
    private String placeId;
    private Place place;

    private String trainIdBack;
    private Train trainBack;
    private String placeIdBack;
    private Place placeBack;


    public ConfirmationBack(Place placeAller, Place placeRetour) {
        this.train = placeAller.getTrain();
        this.place = placeAller;
        trainId = placeAller.getTrain().getId();
        placeId = placeAller.getIdPlace();

        this.trainBack = placeRetour.getTrain();
        this.placeBack = placeRetour;
        trainIdBack = placeRetour.getTrain().getId();
        placeIdBack = placeRetour.getIdPlace();
    }
}
