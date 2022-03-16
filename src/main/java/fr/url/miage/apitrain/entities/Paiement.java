package fr.url.miage.apitrain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paiement {

    private String placeId;
    private float price;

    public Paiement(Place place) {
        this.placeId = place.getIdPlace();
        this.price = place.getPrice();
    }
}
