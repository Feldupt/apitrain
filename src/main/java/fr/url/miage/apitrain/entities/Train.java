package fr.url.miage.apitrain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Train {

    @Id
    private String id;
    private String name;
    private int numberOfPlaceFirstClass;
    private int numberOfPlaceSecondClass;
    private boolean isBar;


}
