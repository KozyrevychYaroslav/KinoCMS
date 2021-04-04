package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.FilmDataDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.FilmData;
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
    private FilmDataDAO filmDataDAO = null;
    @Autowired
    private CinemaDAO cinemaDAO = null;

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

        filmDataDAO.save(filmData);
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

        assertEquals(filmData, filmDataDAO.get("Film title #1"));
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

        filmDataDAO.save(filmData);

        assertEquals(filmData, filmDataDAO.get("Film title #2"));

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

        assertEquals(filmData1, filmDataDAO.get("Film title #1"));

        assertEquals(2, filmDataDAO.getAll().size());

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
        filmDataDAO.save(filmData);

        assertEquals(3, filmDataDAO.getAll().size());

        filmDataDAO.delete("delete");

        assertEquals(2, filmDataDAO.getAll().size());

        assertNull(filmDataDAO.get("delete"));
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

        FilmData filmData1 = filmDataDAO.get("Film title #1");

        filmData1.setFilmDescription("Some description #1 changed");
        filmData1.setFilmTitle("Film title #1 changed");
        filmDataDAO.update(filmData1);

        assertEquals(filmDataDAO.get("Film title #1 changed"), filmData);
    }

    @Test
    @DisplayName("Testing many to many with Cinema table")
    @Order(7)
    public void m6() {
        FilmData filmData = filmDataDAO.get("Film title #1 changed");
        Cinema cinema = new Cinema();
        Cinema cinema1 = new Cinema();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        cinema1.setAddress("Бочарова 60");
        cinema1.setCinemaName("Бочарова");
        cinema1.setInfo("some info2");

        cinema.setFilmsData(Collections.singleton(filmData));
        cinema1.setFilmsData(new LinkedHashSet<>(Arrays.asList(filmData, filmDataDAO.get("Film title #2"))));
        cinemaDAO.save(cinema);
        cinemaDAO.save(cinema1);

        assertEquals(Set.of(cinema, cinema1), filmDataDAO.getCinemas(1));

        // cinema теперь имеет несколько filmData
        cinema.setFilmsData(new HashSet<>(Arrays.asList(filmDataDAO.get("Film title #1 changed"), filmDataDAO.get("Film title #2"))));
        cinemaDAO.update(cinema);
    }

}
