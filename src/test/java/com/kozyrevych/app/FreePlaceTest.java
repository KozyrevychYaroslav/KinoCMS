package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.FilmHall;
import com.kozyrevych.app.model.FreePlace;
import com.kozyrevych.app.model.User;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.FilmHallService;
import com.kozyrevych.app.services.FreePlaceService;
import com.kozyrevych.app.services.UserService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FreePlaceTest {
    @Autowired
    private FreePlaceService freePlaceService = null;
    @Autowired
    private UserService userService = null;
    @Autowired
    private FilmHallService filmHallService = null;
    @Autowired
    private CinemaService cinemaService = null;

    @Test
    @DisplayName("Add and get data to freePlace table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        FilmHall filmHall = new FilmHall();
        User user = new User();
        FreePlace freePlace = new FreePlace();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(10);
        cinema.setFilmHalls(Collections.singleton(filmHall));
        cinemaService.save(cinema);

        user.setBirthday(LocalDate.of(2001, 6, 15));
        user.setEmail("user1@mailru");
        user.setName("name 1");
        user.setPassword("password 1");
        user.setPhoneNumber("+380677157636");
        user.setRegistrationDate(LocalDate.now());

        freePlace.setPlaceNumber(12);
        freePlace.setBooked(true);
        freePlace.setRowNumber(2);
        freePlace.setFilmHall(filmHall);
        freePlace.setUser(user);
        user.setFreePlaces(Collections.singleton(freePlace));
        userService.save(user);

        assertEquals(freePlace, freePlaceService.get(1L));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        FreePlace freePlace = new FreePlace();

        freePlace.setPlaceNumber(15);
        freePlace.setBooked(true);
        freePlace.setRowNumber(2);

        //в одном зале теперь есть два занятых места
        freePlace.setFilmHall(filmHallService.getByFilmHallNumber(10));
        freePlace.setUser(userService.getByPhoneNumber("+380677157636"));
        freePlaceService.save(freePlace);

        assertEquals(Set.of(freePlaceService.get(1L), freePlace), userService.getFreePlaces("+380677157636"));
        assertEquals(Set.of(freePlaceService.get(1L), freePlace), filmHallService.getFreePlaces(1));
    }


    @Test
    @DisplayName("Get all rows from freePlace table and currentFilmDate")
    @Order(4)
    public void m4() {
        assertEquals(2, freePlaceService.getAll().size());
        assertEquals(2, userService.getFreePlaces("+380677157636").size());
        assertEquals(2, filmHallService.getFreePlaces(1).size());
    }

    @Test
    @DisplayName("Delete data from freePlace table using user, freePlace and filmHall")
    @Order(5)
    public void m5() {
        FreePlace freePlace = new FreePlace();
        User user = userService.getByPhoneNumber("+380677157636");

        freePlace.setPlaceNumber(20);
        freePlace.setBooked(true);
        freePlace.setRowNumber(4);
        freePlace.setFilmHall(filmHallService.getByFilmHallNumber(10));
        freePlace.setUser(user);
        freePlaceService.save(freePlace);

        assertEquals(3, freePlaceService.getAll().size());

        userService.deleteByPhoneNumber("+380677157636");

        assertEquals(0, userService.getAll().size());

        assertEquals(0, freePlaceService.getAll().size(), "каскадное удаление не работает");

        assertNull(userService.getByPhoneNumber("+380677157636"));
    }

    @Test
    @DisplayName("update data in freePlace table")
    @Order(6)
    public void m6() {
        User user = new User();
        FreePlace freePlace = new FreePlace();

        user.setBirthday(LocalDate.of(2000, 4, 15));
        user.setEmail("user2@mailru");
        user.setName("name 2");
        user.setPassword("password 2");
        user.setPhoneNumber("+380677157655");
        user.setRegistrationDate(LocalDate.now());


        freePlace.setPlaceNumber(12);
        freePlace.setBooked(true);
        freePlace.setRowNumber(2);
        freePlace.setFilmHall(filmHallService.getByFilmHallNumber(10));
        freePlace.setUser(userService.getByPhoneNumber("+380677157655"));
        user.setFreePlaces(Collections.singleton(freePlace));
        userService.save(user);

        freePlace.setPlaceNumber(20);
        freePlaceService.update(freePlace);

        assertEquals(freePlace, freePlaceService.get(4L));

        freePlaceService.deleteById(4L);

        assertNull(freePlaceService.get(4L));

        assertNotNull(userService.getByPhoneNumber("+380677157655"));
        assertNotNull(filmHallService.getByFilmHallNumber(10));
    }


}
