package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.*;

public interface TrainResource extends JpaRepository<Train, String> {

    List<Train> findByStartCityAndJourneyIn(String startCity, Set<String> journey);

    List<Train> findAllByStartCityAndJourneyInAndDateIsGreaterThanEqual(String city1, Set<String> journey, LocalDateTime date);
}
