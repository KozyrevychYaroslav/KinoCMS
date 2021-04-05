package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.FilmHall;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.FilmHallService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmHallTest {
    @Autowired
    private FilmHallService filmHallService = null;
    @Autowired
    private CinemaService cinemaService = null;

    @Test
    @DisplayName("Add and get data to filmHall table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        FilmHall filmHall = new FilmHall();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(10);
        cinema.setFilmHalls(Collections.singleton(filmHall));
        cinemaService.save(cinema);

        assertEquals(filmHall, filmHallService.getByFilmHallNumber(10));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        FilmHall filmHall = new FilmHall();

        filmHall.setCinema(cinemaService.getByName("Высоцкого"));
        filmHall.setInfo("Film hall №2 info");
        filmHall.setFilmHallNumber(11);

        filmHallService.save(filmHall);

        assertEquals(Set.of(filmHallService.getByFilmHallNumber(10), filmHall), cinemaService.getFilmHalls("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from filmHall table")
    @Order(4)
    public void m4() {
        assertEquals(2, filmHallService.getAll().size());
    }

    @Test
    @DisplayName("Delete data from filmHall table using Cinema and filmHall")
    @Order(5)
    public void m5() {
        FilmHall filmHall = new FilmHall();
        Cinema cinema = cinemaService.getByName("Высоцкого");

        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №3 info");
        filmHall.setFilmHallNumber(12);
        filmHallService.save(filmHall);

        assertEquals(3, filmHallService.getAll().size());

        cinemaService.deleteByName(cinema.getCinemaName());

        assertEquals(0, cinemaService.getAll().size());

        assertEquals(0, filmHallService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
    }

    @Test
    @DisplayName("update data in filmHall table")
    @Order(6)
    public void m6() {
        Cinema cinema = new Cinema();
        FilmHall filmHall = new FilmHall();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");


        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(1);
        filmHall.setCinema(cinema);
        cinema.setFilmHalls(Collections.singleton(filmHall));
        cinemaService.save(cinema);

        filmHall.setFilmHallNumber(40);
        filmHallService.update(filmHall);

        assertEquals(filmHall, filmHallService.getByFilmHallNumber(40));

        filmHallService.deleteByFilmHallNumber(40);

        assertNull(filmHallService.getByFilmHallNumber(40));

        assertNotNull(cinemaService.getByName("Высоцкого"));
    }


}
