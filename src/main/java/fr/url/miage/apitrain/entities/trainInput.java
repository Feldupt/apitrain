package fr.url.miage.apitrain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.ElementCollection;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class trainInput {

    @NotNull
    private String id;
    @NotNull
    @Size(min=1)
    private String name;
    @NotNull
    @ElementCollection
    private Set<String> journey;
    @NotNull
    @Min(0)
    private int numberOfPlaceFirstClass;
    @NotNull
    @Min(0)
    private int numberOfPlaceSecondClass;
    @NotNull
    private boolean isBar;
    @NotNull
    private String startCity;
    @NotNull
    private LocalDateTime date;
}
