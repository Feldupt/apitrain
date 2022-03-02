package fr.url.miage.apitrain.boundary;

import fr.url.miage.apitrain.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface trainResource extends JpaRepository<Train, String> {

}
