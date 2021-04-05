package com.kozyrevych.app;

import com.kozyrevych.app.model.*;
import com.kozyrevych.app.services.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService = null;
    @Autowired
    private CurrentFilmDataService currentFilmDataService = null;
    @Autowired
    private FilmHallService filmHallService = null;
    @Autowired
    private CinemaService cinemaService = null;
    @Autowired
    private FilmDataService filmDataService = null;

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

        userService.save(user);
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

        assertEquals(user, userService.getByPhoneNumber("+380677157636"));
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

        userService.save(user);

        assertEquals(user, userService.getByPhoneNumber("+380677157655"));

        user1.setBirthday(LocalDate.of(2001,6,15));
        user1.setEmail("user1@mailru");
        user1.setName("name 1");
        user1.setPassword("password 1");
        user1.setPhoneNumber("+380677157636");
        user1.setRegistrationDate(LocalDate.now());

        assertEquals(user1, userService.getByPhoneNumber("+380677157636"));

        assertEquals(2, userService.getAll().size());

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
        userService.save(user);

        assertEquals(3, userService.getAll().size());

        userService.deleteByPhoneNumber("+380677157666");

        assertEquals(2, userService.getAll().size());

        assertNull(userService.getByPhoneNumber("+380677157666"));
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

        User user1 = userService.getByPhoneNumber("+380677157636");

        user1.setPassword("password 1 changed");
        userService.update(user1);

        assertEquals(user, userService.getByPhoneNumber("+380677157636"));
    }

    @Test
    @DisplayName("Testing many to many with currentFilmData table")
    @Order(7)
    public void m6() {
        Cinema cinema = new Cinema();
        FilmHall filmHall = new FilmHall();
        User user1 = userService.getByPhoneNumber("+380677157636");
        User user2 = userService.getByPhoneNumber("+380677157655");
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
        cinemaService.save(cinema);

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
        filmDataService.save(filmData);

        currentFilmData1.setFilmTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData1.setDBOX(false);
        currentFilmData1.set3D(false);
        currentFilmData1.setDBOX(true);
        currentFilmData1.setPrice(150);
        currentFilmData1.setVip(false);
        currentFilmData1.setFilmData(filmData);
        currentFilmData1.setFilmHall(filmHall);
        currentFilmDataService.save(currentFilmData1);

        user1.setCurrentFilmsData(Collections.singleton(currentFilmData));
        user2.setCurrentFilmsData(Collections.singleton(currentFilmData));
        userService.update(user1);
        userService.update(user2);

        assertEquals(Set.of(user1, user2), currentFilmDataService.getUsers(1));

        //у user с телефоном +380677157636 всего один currentfilmdata
        assertEquals("[" + currentFilmDataService.get(1L) + "]", userService.getCurrentFilmDatas("+380677157636").toString());

        //у user с телефоном +380677157655 всего один currentfilmdata равный первому user
        assertEquals("[" + currentFilmDataService.get(1L)  + "]", userService.getCurrentFilmDatas("+380677157655").toString());

        // user теперь имеет несколько currentFilmData
        user1.setCurrentFilmsData(new HashSet<>(Arrays.asList(currentFilmDataService.get(1L), currentFilmDataService.get(2L))));
        userService.update(user1);

        assertEquals(userService.getCurrentFilmDatas("+380677157636"), Set.of(currentFilmData,currentFilmData1));

        assertEquals("[" + currentFilmDataService.get(1L) + "]", userService.getCurrentFilmDatas("+380677157655").toString());
    }

    @Test
    @DisplayName("Testing many to many deletion")
    @Order(8)
    public void m7() {
        assertEquals(1, userService.getCurrentFilmDatas("+380677157655").size());

        assertEquals(userService.getCurrentFilmDatas("+380677157636"), Set.of(currentFilmDataService.get(1L), currentFilmDataService.get(2L)));

        currentFilmDataService.deleteById(2);

        // после удаления currentFilmData связи из внешней таблицы связи пропадут
        assertEquals(userService.getCurrentFilmDatas("+380677157636"), Set.of(currentFilmDataService.get(1L)));

        //после удаления CurrentFilmData аккаунты user не должны удаляться
        assertEquals(userService.getAll().size(), 2L);

        assertEquals(1L, currentFilmDataService.getAll().size());

        userService.deleteByPhoneNumber("+380677157655");

        assertEquals(userService.getAll().size(), 1L);

        assertEquals(currentFilmDataService.getAll().size(), 1L);
    }

}
