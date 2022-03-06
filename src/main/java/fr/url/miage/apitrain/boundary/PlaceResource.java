package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PlaceResource extends JpaRepository<Place, String> {

   Iterable<Place> findAllByTrainId(String trainId);

   Iterable<Place> findAllByTrainIdAndIsWindow(String trainId, boolean window);

}
