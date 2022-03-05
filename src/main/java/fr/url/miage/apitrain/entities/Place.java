package fr.url.miage.apitrain.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name="train_id")
    private Train train;
    private boolean isWindow;


}
