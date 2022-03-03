package fr.url.miage.apitrain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    @ElementCollection
    private List<String> felzer;
    private int numberOfPlaceFirstClass;
    private int numberOfPlaceSecondClass;
    private boolean isBar;


}
