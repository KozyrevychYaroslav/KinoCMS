package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.FilmData;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.FilmDataService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class FilmDataTest {
    @Autowired
    private FilmDataService filmDataService = null;
    @Autowired
    private CinemaService cinemaService = null;

    @Test
    @DisplayName("Adding data to filmData table")
    @Order(2)
    public void m1() {
        FilmData filmData = new FilmData();

        filmData.setBudget(125_000);
        filmData.setFilmTitle("Film title #1");
        filmData.setFilmLength("95 минут");
        filmData.setComposer("Composer #1");
        filmData.setCountry("Ukraine");
        filmData.setDirector("Director #1");
        filmData.setFilmDescription("Some description #1");
        filmData.setGenre("Horror");
        filmData.setMinimumAge(18);
        filmData.setProducer("Producer #1");
        filmData.setOperator("Operator #1");
        filmData.setYear(2021);

        filmDataService.save(filmData);
    }

    @Test
    @DisplayName("Get data from filmData table")
    @Order(3)
    public void m2() {
        FilmData filmData = new FilmData();

        filmData.setBudget(125_000);
        filmData.setFilmTitle("Film title #1");
        filmData.setFilmLength("95 минут");
        filmData.setComposer("Composer #1");
        filmData.setCountry("Ukraine");
        filmData.setDirector("Director #1");
        filmData.setFilmDescription("Some description #1");
        filmData.setGenre("Horror");
        filmData.setMinimumAge(18);
        filmData.setProducer("Producer #1");
        filmData.setOperator("Operator #1");
        filmData.setYear(2021);

        assertEquals(filmData, filmDataService.getByTitle("Film title #1"));
    }

    @Test
    @DisplayName("Get all rows from filmData table")
    @Order(4)
    public void m3() {
        FilmData filmData = new FilmData();
        FilmData filmData1 = new FilmData();

        filmData.setBudget(125_000);
        filmData.setFilmTitle("Film title #2");
        filmData.setFilmLength("100 минут");
        filmData.setComposer("Composer #2");
        filmData.setCountry("USA");
        filmData.setDirector("Director #2");
        filmData.setFilmDescription("Some description #2");
        filmData.setGenre("Horror");
        filmData.setMinimumAge(18);
        filmData.setProducer("Producer #2");
        filmData.setOperator("Operator #2");
        filmData.setYear(2021);

        filmDataService.save(filmData);

        assertEquals(filmData, filmDataService.getByTitle("Film title #2"));

        filmData1.setBudget(125_000);
        filmData1.setFilmTitle("Film title #1");
        filmData1.setFilmLength("95 минут");
        filmData1.setComposer("Composer #1");
        filmData1.setCountry("Ukraine");
        filmData1.setDirector("Director #1");
        filmData1.setFilmDescription("Some description #1");
        filmData1.setGenre("Horror");
        filmData1.setMinimumAge(18);
        filmData1.setProducer("Producer #1");
        filmData1.setOperator("Operator #1");
        filmData1.setYear(2021);

        assertEquals(filmData1, filmDataService.getByTitle("Film title #1"));

        assertEquals(2, filmDataService.getAll().size());

    }

    @Test
    @DisplayName("Delete data from filmData table")
    @Order(5)
    public void m4() {
        FilmData filmData = new FilmData();

        filmData.setBudget(125_000);
        filmData.setFilmTitle("delete");
        filmData.setFilmLength("delete");
        filmData.setComposer("delete");
        filmData.setCountry("delete");
        filmData.setDirector("delete");
        filmData.setFilmDescription("delete");
        filmData.setGenre("delete");
        filmData.setMinimumAge(18);
        filmData.setProducer("delete");
        filmData.setOperator("delete");
        filmData.setYear(2021);
        filmDataService.save(filmData);

        assertEquals(3, filmDataService.getAll().size());

        filmDataService.deleteByTitle("delete");

        assertEquals(2, filmDataService.getAll().size());

        assertNull(filmDataService.getByTitle("delete"));
    }

    @Order(6)
    @DisplayName("Update filmData")
    @Test
    public void m5() {
        FilmData filmData = new FilmData();

        filmData.setBudget(125_000);
        filmData.setFilmTitle("Film title #1 changed");
        filmData.setFilmLength("95 минут");
        filmData.setComposer("Composer #1");
        filmData.setCountry("Ukraine");
        filmData.setDirector("Director #1");
        filmData.setFilmDescription("Some description #1 changed");
        filmData.setGenre("Horror");
        filmData.setMinimumAge(18);
        filmData.setProducer("Producer #1");
        filmData.setOperator("Operator #1");
        filmData.setYear(2021);

        FilmData filmData1 = filmDataService.getByTitle("Film title #1");

        filmData1.setFilmDescription("Some description #1 changed");
        filmData1.setFilmTitle("Film title #1 changed");
        filmDataService.update(filmData1);

        assertEquals(filmDataService.getByTitle("Film title #1 changed"), filmData);
    }

    @Test
    @DisplayName("Testing many to many with Cinema table")
    @Order(7)
    public void m6() {
        FilmData filmData = filmDataService.getByTitle("Film title #1 changed");
        Cinema cinema = new Cinema();
        Cinema cinema1 = new Cinema();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        cinema1.setAddress("Бочарова 60");
        cinema1.setCinemaName("Бочарова");
        cinema1.setInfo("some info2");


        cinema.setFilmsData(new LinkedHashSet<>(Arrays.asList(filmData)));
        cinema1.setFilmsData(new LinkedHashSet<>(Arrays.asList(filmData, filmDataService.getByTitle("Film title #2"))));
        cinemaService.save(cinema);
        cinemaService.save(cinema1);
        assertEquals(Set.of(cinema, cinema1), filmDataService.getCinemas(1));

        //cinema теперь имеет несколько filmData
        cinema.setFilmsData(new LinkedHashSet<>(Arrays.asList(filmDataService.getByTitle("Film title #1 changed"), filmDataService.getByTitle("Film title #2"))));
        cinemaService.update(cinema);
    }

}
