package com.kozyrevych.app;

import com.kozyrevych.app.model.*;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.CurrentFilmDataService;
import com.kozyrevych.app.services.FilmDataService;
import com.kozyrevych.app.services.FilmHallService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurrentFilmDataTest {
    @Autowired
    private CurrentFilmDataService currentFilmDataService = null;
    @Autowired
    private FilmDataService filmDataService = null;
    @Autowired
    private FilmHallService filmHallService = null;
    @Autowired
    private CinemaService cinemaService = null;

    @Test
    @DisplayName("Add and get data to currentFilmData table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        FilmHall filmHall = new FilmHall();
        FilmData filmData = new FilmData();
        CurrentFilmData currentFilmData = new CurrentFilmData();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(10);
        filmHall.setCinema(cinema);
        cinema.setFilmHalls(Collections.singleton(filmHall));

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
        cinema.setFilmsData(Collections.singleton(filmData));

        currentFilmData.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(true);
        currentFilmData.set3D(false);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(90);
        currentFilmData.setVip(true);
        currentFilmData.setFilmData(filmData);
        currentFilmData.setFilmHall(filmHall);
        filmHall.setCurrentFilmsData(Collections.singleton(currentFilmData));
        filmData.setCurrentFilmData(Collections.singleton(currentFilmData));

        cinemaService.save(cinema);

        assertEquals(currentFilmData, currentFilmDataService.get(1L));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        CurrentFilmData currentFilmData = new CurrentFilmData();

        currentFilmData.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(false);
        currentFilmData.set3D(false);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(150);
        currentFilmData.setVip(false);
        currentFilmData.setFilmData(filmDataService.getByTitle("Film title #1"));

        //в одном зале теперь показываются два фильма одного типа, в разное время
        currentFilmData.setFilmHall(filmHallService.getByFilmHallNumber(10));
        currentFilmDataService.save(currentFilmData);


        assertEquals(Set.of(currentFilmDataService.get(1L), currentFilmData), filmDataService.getCurrentFilmDatas(1));
        assertEquals(Set.of(currentFilmDataService.get(1L), currentFilmData), filmHallService.getCurrentFilmDatas(1));
    }


    @Test
    @DisplayName("Get all rows from currentFilmData table and currentFilmDate")
    @Order(4)
    public void m4() {
        assertEquals(2, currentFilmDataService.getAll().size());
        assertEquals(2, filmDataService.getCurrentFilmDatas(1).size());
        assertEquals(2, filmHallService.getCurrentFilmDatas(1).size());
    }

    @Test
    @DisplayName("Delete data from currentFilmData table using filmData, currentFilmData and filmHall")
    @Order(5)
    public void m5() {
        CurrentFilmData currentFilmData = new CurrentFilmData();
        FilmData filmData = filmDataService.getByTitle("Film title #1");

        currentFilmData.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(true);
        currentFilmData.set3D(true);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(200);
        currentFilmData.setVip(true);
        currentFilmData.setFilmData(filmData);
        currentFilmData.setFilmHall(filmHallService.getByFilmHallNumber(10));

        currentFilmDataService.save(currentFilmData);

        assertEquals(3, currentFilmDataService.getAll().size());

        filmDataService.deleteByTitle("Film title #1");

        assertEquals(0, filmDataService.getAll().size());

        assertEquals(0, currentFilmDataService.getAll().size(), "каскадное удаление не работает");

        assertNull(filmDataService.getByTitle("Film title #1"));
    }

    @Test
    @DisplayName("update data in currentFilmData table")
    @Order(6)
    public void m6() {
        FilmData filmData = new FilmData();
        CurrentFilmData currentFilmData = new CurrentFilmData();

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

        currentFilmData.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(true);
        currentFilmData.set3D(false);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(90);
        currentFilmData.setVip(true);
        currentFilmData.setFilmData(filmData);
        currentFilmData.setFilmHall(filmHallService.getByFilmHallNumber(10));
        filmData.setCurrentFilmData(Collections.singleton(currentFilmData));
        filmDataService.save(filmData);

        currentFilmData.setPrice(123);
        currentFilmDataService.update(currentFilmData);

        assertEquals(currentFilmData, currentFilmDataService.get(4L));

        currentFilmDataService.deleteById(4L);

        assertNull(currentFilmDataService.get(4L));

        assertNotNull(filmDataService.getByTitle("Film title #1"));
        assertNotNull(filmHallService.getByFilmHallNumber(10));
    }


}
