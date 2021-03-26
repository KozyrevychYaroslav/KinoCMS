package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.CurrentFilmDataDAO;
import com.kozyrevych.app.dao.FilmDataDAO;
import com.kozyrevych.app.dao.FilmHallDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FilmData;
import com.kozyrevych.app.model.FilmHall;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CurrentFilmDataTest {
    private static SessionFactory sessionFactory = null;
    private static CurrentFilmDataDAO currentFilmDataDAO = null;
    private static FilmDataDAO filmDataDAO = null;
    private static FilmHallDAO filmHallDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        filmDataDAO = new FilmDataDAO(sessionFactory);
        currentFilmDataDAO = new CurrentFilmDataDAO(sessionFactory);
        filmHallDAO = new FilmHallDAO(sessionFactory);
        cinemaDAO = new CinemaDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to currentFilmData table")
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
        filmDataDAO.save(filmData);

        currentFilmData.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(true);
        currentFilmData.set3D(false);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(90);
        currentFilmData.setVip(true);
        currentFilmData.setFilmData(filmData);
        currentFilmData.setFilmHall(filmHall);
        currentFilmDataDAO.save(currentFilmData);

        assertEquals(currentFilmData, currentFilmDataDAO.get(1));
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
        currentFilmData.setFilmData(filmDataDAO.get("Film title #1"));

        //в одном зале теперь показываются два фильма одного типа, в разное время
        currentFilmData.setFilmHall(filmHallDAO.get(10));
        currentFilmDataDAO.save(currentFilmData);

        assertEquals(Set.of(currentFilmDataDAO.get(1), currentFilmData), filmDataDAO.getCurrentFilmDatas(1));
        assertEquals(Set.of(currentFilmDataDAO.get(1), currentFilmData), filmHallDAO.getCurrentFilmDatas(1));
    }


    @Test
    @DisplayName("Get all rows from currentFilmData table and currentFilmDate")
    @Order(4)
    public void m4() {
        assertEquals(2, currentFilmDataDAO.getAll().size());
        assertEquals(2, filmDataDAO.getCurrentFilmDatas(1).size());
        assertEquals(2, filmHallDAO.getCurrentFilmDatas(1).size());
    }

    @Test
    @DisplayName("Delete data from currentFilmData table using filmData, currentFilmData and filmHall")
    @Order(5)
    public void m5() {
        CurrentFilmData currentFilmData = new CurrentFilmData();
        FilmData filmData = filmDataDAO.get("Film title #1");

        currentFilmData.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(true);
        currentFilmData.set3D(true);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(200);
        currentFilmData.setVip(true);
        currentFilmData.setFilmData(filmData);
        currentFilmData.setFilmHall(filmHallDAO.get(10));

        currentFilmDataDAO.save(currentFilmData);

        assertEquals(3, currentFilmDataDAO.getAll().size());

        filmDataDAO.delete("Film title #1");

        assertEquals(0, filmDataDAO.getAll().size());

        assertEquals(0, currentFilmDataDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(filmDataDAO.get("Film title #1"));
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
        filmDataDAO.save(filmData);

        currentFilmData.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(true);
        currentFilmData.set3D(false);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(90);
        currentFilmData.setVip(true);
        currentFilmData.setFilmData(filmData);
        currentFilmData.setFilmHall(filmHallDAO.get(10));
        currentFilmDataDAO.save(currentFilmData);

        currentFilmData.setPrice(123);
        currentFilmDataDAO.update(currentFilmData);

        assertEquals(currentFilmData, currentFilmDataDAO.get(4));

        currentFilmDataDAO.delete(4);

        assertNull(currentFilmDataDAO.get(4));

        assertNotNull(filmDataDAO.get("Film title #1"));
        assertNotNull(filmHallDAO.get(10));
    }


}
