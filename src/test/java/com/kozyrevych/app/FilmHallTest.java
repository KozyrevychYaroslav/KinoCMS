package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.FilmHallDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.FilmHall;
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
    private FilmHallDAO filmHallDAO = null;
    @Autowired
    private CinemaDAO cinemaDAO = null;

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
        cinemaDAO.save(cinema);

        assertEquals(filmHall, filmHallDAO.get(10));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        FilmHall filmHall = new FilmHall();

        filmHall.setCinema(cinemaDAO.get("Высоцкого"));
        filmHall.setInfo("Film hall №2 info");
        filmHall.setFilmHallNumber(11);

        filmHallDAO.save(filmHall);

        assertEquals(Set.of(filmHallDAO.get(10), filmHall), cinemaDAO.getFilmHalls("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from filmHall table")
    @Order(4)
    public void m4() {
        assertEquals(2, filmHallDAO.getAll().size());
    }

    @Test
    @DisplayName("Delete data from filmHall table using Cinema and filmHall")
    @Order(5)
    public void m5() {
        FilmHall filmHall = new FilmHall();
        Cinema cinema = cinemaDAO.get("Высоцкого");

        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №3 info");
        filmHall.setFilmHallNumber(12);
        filmHallDAO.save(filmHall);

        assertEquals(3, filmHallDAO.getAll().size());

        cinemaDAO.delete(cinema.getCinemaName());

        assertEquals(0, cinemaDAO.getAll().size());

        assertEquals(0, filmHallDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));
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

        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(1);
        cinema.setFilmHalls(Collections.singleton(filmHall));
        cinemaDAO.save(cinema);

        filmHall.setFilmHallNumber(40);
        filmHallDAO.update(filmHall);

        assertEquals(filmHall, filmHallDAO.get(40));

        filmHallDAO.delete(40);

        assertNull(filmHallDAO.get(40));

        assertNotNull(cinemaDAO.get("Высоцкого"));
    }


}
