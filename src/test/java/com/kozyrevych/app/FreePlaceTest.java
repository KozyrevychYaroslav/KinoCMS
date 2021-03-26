package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.FreePlaceDAO;
import com.kozyrevych.app.dao.UserDAO;
import com.kozyrevych.app.dao.UserDAO;
import com.kozyrevych.app.dao.FilmHallDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.FreePlace;
import com.kozyrevych.app.model.User;
import com.kozyrevych.app.model.FilmHall;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FreePlaceTest {
    private static SessionFactory sessionFactory = null;
    private static FreePlaceDAO freePlaceDAO = null;
    private static UserDAO userDAO = null;
    private static FilmHallDAO filmHallDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        userDAO = new UserDAO(sessionFactory);
        freePlaceDAO = new FreePlaceDAO(sessionFactory);
        filmHallDAO = new FilmHallDAO(sessionFactory);
        cinemaDAO = new CinemaDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to freePlace table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        FilmHall filmHall = new FilmHall();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(10);
        filmHallDAO.save(filmHall);

        User user = new User();
        FreePlace freePlace = new FreePlace();

        user.setBirthday(LocalDate.of(2001,6,15));
        user.setEmail("user1@mailru");
        user.setName("name 1");
        user.setPassword("password 1");
        user.setPhoneNumber("+380677157636");
        user.setRegistrationDate(LocalDate.now());
        userDAO.save(user);

        freePlace.setPlaceNumber(12);
        freePlace.setBooked(true);
        freePlace.setRowNumber(2);
        freePlace.setFilmHall(filmHall);
        freePlace.setUser(user);
        freePlaceDAO.save(freePlace);

        assertEquals(freePlace, freePlaceDAO.get(1));
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
        freePlace.setFilmHall(filmHallDAO.get(10));
        freePlace.setUser(userDAO.get("+380677157636"));
        freePlaceDAO.save(freePlace);

        assertEquals(Set.of(freePlaceDAO.get(1), freePlace), userDAO.getFreePlaces(1));
        assertEquals(Set.of(freePlaceDAO.get(1), freePlace), filmHallDAO.getFreePlaces(1));
    }


    @Test
    @DisplayName("Get all rows from freePlace table and currentFilmDate")
    @Order(4)
    public void m4() {
        assertEquals(2, freePlaceDAO.getAll().size());
        assertEquals(2, userDAO.getFreePlaces(1).size());
        assertEquals(2, filmHallDAO.getFreePlaces(1).size());
    }

    @Test
    @DisplayName("Delete data from freePlace table using user, freePlace and filmHall")
    @Order(5)
    public void m5() {
        FreePlace freePlace = new FreePlace();
        User user = userDAO.get("+380677157636");

        freePlace.setPlaceNumber(20);
        freePlace.setBooked(true);
        freePlace.setRowNumber(4);
        freePlace.setFilmHall(filmHallDAO.get(10));
        freePlace.setUser(user);
        freePlaceDAO.save(freePlace);

        assertEquals(3, freePlaceDAO.getAll().size());

        userDAO.delete("+380677157636");

        assertEquals(0, userDAO.getAll().size());

        assertEquals(0, freePlaceDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(userDAO.get("+380677157636"));
    }

    @Test
    @DisplayName("update data in freePlace table")
    @Order(6)
    public void m6() {
        User user = new User();
        FreePlace freePlace = new FreePlace();

        user.setBirthday(LocalDate.of(2000,4,15));
        user.setEmail("user2@mailru");
        user.setName("name 2");
        user.setPassword("password 2");
        user.setPhoneNumber("+380677157655");
        user.setRegistrationDate(LocalDate.now());
        userDAO.save(user);

        freePlace.setPlaceNumber(12);
        freePlace.setBooked(true);
        freePlace.setRowNumber(2);
        freePlace.setFilmHall(filmHallDAO.get(10));
        freePlace.setUser(userDAO.get("+380677157655"));
        freePlaceDAO.save(freePlace);

        freePlace.setPlaceNumber(20);
        freePlaceDAO.update(freePlace);

        assertEquals(freePlace, freePlaceDAO.get(4));

        freePlaceDAO.delete(4);

        assertNull(freePlaceDAO.get(4));

        assertNotNull(userDAO.get("+380677157655"));
        assertNotNull(filmHallDAO.get(10));
    }


}
