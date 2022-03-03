package fr.url.miage.apitrain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class trainInput {

    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    @ElementCollection
    private List<String> felzer;
    @NotNull
    private int numberOfPlaceFirstClass;
    @NotNull
    private int numberOfPlaceSecondClass;
    @NotNull
    private boolean isBar;
}
