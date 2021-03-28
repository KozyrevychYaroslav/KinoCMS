package com.kozyrevych.app;

import com.kozyrevych.app.dao.*;
import com.kozyrevych.app.model.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static SessionFactory sessionFactory = null;
    private static UserDAO userDAO = null;
    private static CurrentFilmDataDAO currentFilmDataDAO = null;
    private static FilmHallDAO filmHallDAO = null;
    private static CinemaDAO cinemaDAO = null;
    private static FilmDataDAO filmDataDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        userDAO = new UserDAO(sessionFactory);
        currentFilmDataDAO = new CurrentFilmDataDAO(sessionFactory);
        filmHallDAO = new FilmHallDAO(sessionFactory);
        cinemaDAO = new CinemaDAO(sessionFactory);
        currentFilmDataDAO = new CurrentFilmDataDAO(sessionFactory);
        filmDataDAO = new FilmDataDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Adding data to user table")
    @Order(2)
    public void m1() {
        User user = new User();

        user.setBirthday(LocalDate.of(2001,6,15));
        user.setEmail("user1@mailru");
        user.setName("name 1");
        user.setPassword("password 1");
        user.setPhoneNumber("+380677157636");
        user.setRegistrationDate(LocalDate.now());

        userDAO.save(user);
    }

    @Test
    @DisplayName("Get data from user table")
    @Order(3)
    public void m2() {
        User user = new User();

        user.setBirthday(LocalDate.of(2001,6,15));
        user.setEmail("user1@mailru");
        user.setName("name 1");
        user.setPassword("password 1");
        user.setPhoneNumber("+380677157636");
        user.setRegistrationDate(LocalDate.now());

        assertEquals(user, userDAO.get("+380677157636"));
    }

    @Test
    @DisplayName("Get all rows from user table")
    @Order(4)
    public void m3() {
        User user = new User();
        User user1 = new User();

        user.setBirthday(LocalDate.of(2000,4,15));
        user.setEmail("user2@mailru");
        user.setName("name 2");
        user.setPassword("password 2");
        user.setPhoneNumber("+380677157655");
        user.setRegistrationDate(LocalDate.now());

        userDAO.save(user);

        assertEquals(user, userDAO.get("+380677157655"));

        user1.setBirthday(LocalDate.of(2001,6,15));
        user1.setEmail("user1@mailru");
        user1.setName("name 1");
        user1.setPassword("password 1");
        user1.setPhoneNumber("+380677157636");
        user1.setRegistrationDate(LocalDate.now());

        assertEquals(user1, userDAO.get("+380677157636"));

        assertEquals(2, userDAO.getAll().size());

    }

    @Test
    @DisplayName("Delete data from user table")
    @Order(5)
    public void m4() {
        User user = new User();

        user.setBirthday(LocalDate.of(2001,6,15));
        user.setEmail("delete");
        user.setName("delete");
        user.setPassword("delete");
        user.setPhoneNumber("+380677157666");
        user.setRegistrationDate(LocalDate.now());
        userDAO.save(user);

        assertEquals(3, userDAO.getAll().size());

        userDAO.delete("+380677157666");

        assertEquals(2, userDAO.getAll().size());

        assertNull(userDAO.get("+380677157666"));
    }

    @Order(6)
    @DisplayName("Update user")
    @Test
    public void m5() {
        User user = new User();

        user.setBirthday(LocalDate.of(2001,6,15));
        user.setEmail("user1@mailru");
        user.setName("name 1");
        user.setPassword("password 1 changed");
        user.setPhoneNumber("+380677157636");
        user.setRegistrationDate(LocalDate.now());

        User user1 = userDAO.get("+380677157636");

        user1.setPassword("password 1 changed");
        userDAO.update(user1);

        assertEquals(user, userDAO.get("+380677157636"));
    }

    @Test
    @DisplayName("Testing many to many with currentFilmData table")
    @Order(7)
    public void m6() {
        Cinema cinema = new Cinema();
        FilmHall filmHall = new FilmHall();
        User user1 = userDAO.get("+380677157636");
        User user2 = userDAO.get("+380677157655");
        FilmData filmData = new FilmData();
        CurrentFilmData currentFilmData = new CurrentFilmData();
        CurrentFilmData currentFilmData1 = new CurrentFilmData();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(10);
        cinema.setFilmHalls(Collections.singleton(filmHall));
        cinemaDAO.save(cinema);

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
        currentFilmData.setFilmHall(filmHall);
        filmData.setCurrentFilmData(Collections.singleton(currentFilmData));
        filmDataDAO.save(filmData);

        currentFilmData1.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData1.setDBOX(false);
        currentFilmData1.set3D(false);
        currentFilmData1.setDBOX(true);
        currentFilmData1.setPrice(150);
        currentFilmData1.setVip(false);
        currentFilmData1.setFilmData(filmData);
        currentFilmData1.setFilmHall(filmHall);
        currentFilmDataDAO.save(currentFilmData1);

        user1.setCurrentFilmsData(Collections.singleton(currentFilmData));
        user2.setCurrentFilmsData(Collections.singleton(currentFilmData));
        userDAO.update(user1);
        userDAO.update(user2);

        assertEquals(Set.of(user1, user2), currentFilmDataDAO.getUsers(1));

        //у user с телефоном +380677157636 всего один currentfilmdata
        assertEquals("[" + currentFilmDataDAO.get(1) + "]", userDAO.getCurrentFilmDatas("+380677157636").toString());

        //у user с телефоном +380677157655 всего один currentfilmdata равный первому user
        assertEquals("[" + currentFilmDataDAO.get(1)  + "]", userDAO.getCurrentFilmDatas("+380677157655").toString());

        // user теперь имеет несколько currentFilmData
        user1.setCurrentFilmsData(new HashSet<>(Arrays.asList(currentFilmDataDAO.get(1), currentFilmDataDAO.get(2))));
        userDAO.update(user1);

        assertEquals(userDAO.getCurrentFilmDatas("+380677157636"), Set.of(currentFilmData,currentFilmData1));

        assertEquals("[" + currentFilmDataDAO.get(1) + "]", userDAO.getCurrentFilmDatas("+380677157655").toString());
    }

    @Test
    @DisplayName("Testing many to many deletion")
    @Order(8)
    public void m7() {
        assertEquals(1, userDAO.getCurrentFilmDatas("+380677157655").size());

        assertEquals(userDAO.getCurrentFilmDatas("+380677157636"), Set.of(currentFilmDataDAO.get(1), currentFilmDataDAO.get(2)));

        currentFilmDataDAO.delete(2);

        // после удаления currentFilmData связи из внешней таблицы связи пропадут
        assertEquals(userDAO.getCurrentFilmDatas("+380677157636"), Set.of(currentFilmDataDAO.get(1)));

        //после удаления CurrentFilmData аккаунты user не должны удаляться
        assertEquals(userDAO.getAll().size(), 2);

        assertEquals(currentFilmDataDAO.getAll().size(), 1);

        userDAO.delete("+380677157655");

        assertEquals(userDAO.getAll().size(), 1);

        assertEquals(currentFilmDataDAO.getAll().size(), 1);
    }

}
