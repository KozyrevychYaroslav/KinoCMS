package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.model.Cinema;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaTest {
    private static SessionFactory sessionFactory = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Adding data to cinema table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
    }

    @Test
    @DisplayName("Get data from cinema table")
    @Order(3)
    public void m2() {
        assertAll(() -> assertEquals("Высоцкого", cinemaDAO.get("Высоцкого").getCinemaName()),
                () -> assertEquals("Высоцкого 50/б", cinemaDAO.get("Высоцкого").getAddress()),
                () -> assertEquals("some info", cinemaDAO.get("Высоцкого").getInfo())
        );
    }

    @Test
    @DisplayName("Get all rows from cinema table")
    @Order(4)
    public void m3() {
        Cinema cinema = new Cinema();

        cinema.setAddress("Аркадия 60/а");
        cinema.setCinemaName("Аркадия");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);

        assertAll(() -> assertEquals("Высоцкого", cinemaDAO.get("Высоцкого").getCinemaName()),
                () -> assertEquals("Высоцкого 50/б", cinemaDAO.get("Высоцкого").getAddress()),
                () -> assertEquals("some info", cinemaDAO.get("Высоцкого").getInfo())
        );

        assertAll(() -> assertEquals("Аркадия", cinemaDAO.get("Аркадия").getCinemaName()),
                () -> assertEquals("Аркадия 60/а", cinemaDAO.get("Аркадия").getAddress()),
                () -> assertEquals("some info", cinemaDAO.get("Аркадия").getInfo())
        );

        assertEquals(2, cinemaDAO.getAll().size());

    }

    @Test
    @DisplayName("Delete data from cinema table")
    @Order(5)
    public void m4() {
        Cinema cinema = new Cinema();

        cinema.setAddress("delete");
        cinema.setCinemaName("delete");
        cinema.setInfo("delete");
        cinemaDAO.save(cinema);

        assertEquals(3, cinemaDAO.getAll().size());

        cinemaDAO.delete("delete");

        assertEquals(2, cinemaDAO.getAll().size());

        assertNull(cinemaDAO.get("delete"));
    }

    @Order(6)
    @DisplayName("Update cinema")
    @Test
    public void m5() {
        Cinema cinema = new Cinema();

        cinema.setAddress("Аркадия 60/а");
        cinema.setCinemaName("Аркадия");
        cinema.setInfo("some info changed");

        Cinema cinema1 = cinemaDAO.get("Аркадия");

        cinema1.setInfo("some info changed");
        cinemaDAO.update(cinema1);

        assertEquals(cinema, cinemaDAO.get("Аркадия"));
    }
}
