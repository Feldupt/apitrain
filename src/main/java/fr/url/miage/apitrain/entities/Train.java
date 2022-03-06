package fr.url.miage.apitrain.entities;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    private Set<String> journey;
    private String startCity;
    @OneToMany
    private Set<Place> firstClassPlace;
    @OneToMany
    private Set<Place> secondClassPlace;
    private int numberOfPlaceFirstClass;
    private int numberOfPlaceSecondClass;
    private boolean isBar;
    private LocalDateTime date;

    public Train(String id) {
        this.id = id;
    }
}
