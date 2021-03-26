package com.kozyrevych.app;

import com.kozyrevych.app.dao.AdvertisingDAO;
import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.Cinema;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AdvertisingTest {
    private static SessionFactory sessionFactory = null;
    private static AdvertisingDAO advertisingDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        advertisingDAO = new AdvertisingDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to Advertising table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Advertising advertising = new Advertising();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        advertising.setCinema(cinema);
        advertising.setInfo("some info 1");
        advertisingDAO.save(advertising);

        assertEquals(advertising, advertisingDAO.get(1));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(advertisingDAO.get(1), cinemaDAO.getAdvertising(1));
    }

    @Test
    @DisplayName("Get all rows from Advertising table")
    @Order(4)
    public void m4() {
        Cinema cinema = new Cinema();
        Advertising advertising = new Advertising();

        cinema.setAddress("Бочароваа 20");
        cinema.setCinemaName("Бочарова");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        advertising.setCinema(cinema);
        advertising.setInfo("some info 2");
        advertisingDAO.save(advertising);

        assertEquals(2, advertisingDAO.getAll().size());
    }


    @Test
    @DisplayName("Delete data from Advertising table using Cinema and Advertising")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaDAO.getAll().size());

        cinemaDAO.delete("Высоцкого");

        assertEquals(1, cinemaDAO.getAll().size());

        assertEquals(1, advertisingDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));
    }

    @Test
    @DisplayName("update data in Advertising table")
    @Order(6)
    public void m6() {
        Advertising advertising = advertisingDAO.get(2);

        advertising.setInfo("UPDATED info");
        advertisingDAO.update(advertising);

        assertEquals(advertising, advertisingDAO.get(2));

        advertisingDAO.delete(2);

        assertNull(advertisingDAO.get(1));

        assertNull(advertisingDAO.get(3));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }
}