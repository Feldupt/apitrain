package fr.url.miage.apitrain;

import fr.url.miage.apitrain.boundary.TrainResource;
import fr.url.miage.apitrain.entities.Place;
import fr.url.miage.apitrain.entities.Train;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainRepresentationTest {

    @LocalServerPort
    int port;

    @Autowired
    TrainResource tr;

    @BeforeEach
    public void setupContext() {
        RestAssured.port = port;
    }

    @Test
    public void getAllTrainTest() {
        Set<String> si = new HashSet<String>();
        si.add("Marseille");
        Set<Place> sp = new HashSet<Place>();
        Set<Place> sp2 = new HashSet<Place>();
        ArrayList<Place> pl2 = new ArrayList<Place>();
        Train train = new Train("1", "RER", si, "Nancy", sp, sp2, 1,1,true, LocalDateTime.now());
        Train train2 = new Train("2", "toto", si, "Nancy", sp, sp2, 1,1,true, LocalDateTime.now());

        tr.save(train);
        tr.save(train2);

        Response response = when().get("train").then().statusCode(HttpStatus.SC_OK).extract().response();
        String responseString = response.asString();
        assertThat(responseString, containsString("RER"));
        assertThat(responseString, containsString("toto"));
    }

    @Test
    public void getOneTrainByIdTest() {
        Set<String> si = new HashSet<String>();
        si.add("Marseille");
        Set<Place> sp = new HashSet<Place>();
        Set<Place> sp2 = new HashSet<Place>();
        ArrayList<Place> pl2 = new ArrayList<Place>();
        Train train = new Train("1", "RER", si, "Nancy", sp, sp2, 1,1,true, LocalDateTime.now());

        tr.save(train);

        Response response = when().get("train/1").then().statusCode(HttpStatus.SC_OK).extract().response();
        String responseString = response.asString();
        assertThat(responseString, containsString("RER"));
    }

    @Test
    public void getOneTrainByCityTest() {
        Set<String> si = new HashSet<String>();
        si.add("Marseille");
        Set<Place> sp = new HashSet<Place>();
        Set<Place> sp2 = new HashSet<Place>();
        ArrayList<Place> pl2 = new ArrayList<Place>();
        Train train = new Train("1", "RER", si, "Nancy", sp, sp2, 1,1,true, LocalDateTime.now());

        tr.save(train);

        Response response = when().get("train/Nancy/Marseille").then().statusCode(HttpStatus.SC_OK).extract().response();
        String responseString = response.asString();
        assertThat(responseString, containsString("RER"));
    }

    @Test
    public void getTrainBySimpleByDayTest() {
        Set<String> si = new HashSet<String>();
        si.add("Marseille");
        Set<Place> sp = new HashSet<Place>();
        Set<Place> sp2 = new HashSet<Place>();
        ArrayList<Place> pl2 = new ArrayList<Place>();
        Train train = new Train("1", "RER", si, "Nancy", sp, sp2, 1,1,true, LocalDateTime.now());

        tr.save(train);

        Response response = when().get("train/Nancy/Marseille/aller/23-03-2022/15").then().statusCode(HttpStatus.SC_OK).extract().response();
        String responseString = response.asString();
        assertThat(responseString, containsString("RER"));
    }

    @Test
    public void getTrainByBackByDayTest() {
        Set<String> si = new HashSet<String>();
        Set<String> si2 = new HashSet<String>();
        si.add("Marseille");
        si2.add("Lille");
        Set<Place> sp = new HashSet<Place>();
        Set<Place> sp2 = new HashSet<Place>();
        ArrayList<Place> pl2 = new ArrayList<Place>();
        Train train = new Train("1", "RER", si, "Nancy", sp, sp2, 1,1,true, LocalDateTime.now());
        Train trainRetour = new Train("2", "toto", si2, "Marseille", sp, sp2, 1,1,true, LocalDateTime.now());

        tr.save(train);

        Response response = when().get("train/Nancy/Marseille/aller-retour/23-03-2022/15/23-03-2022/16").then().statusCode(HttpStatus.SC_OK).extract().response();
        String responseString = response.asString();
        assertThat(responseString, containsString("RER"));
    }






}
