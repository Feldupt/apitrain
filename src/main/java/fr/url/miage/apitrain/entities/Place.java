package fr.url.miage.apitrain.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    private String idPlace;
    private boolean isWindow;
    private boolean occupied;
    @ManyToOne
    private Train train;



}
