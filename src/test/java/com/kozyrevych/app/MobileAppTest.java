package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.MobileAppDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.MobileApp;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class MobileAppTest {
    private static SessionFactory sessionFactory = null;
    private static MobileAppDAO mobileAppDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        mobileAppDAO = new MobileAppDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to mobileApp table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        MobileApp mobileApp = new MobileApp();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        mobileApp.setCinema(cinema);
        mobileApp.setInfo("some info 1");
        cinema.setMobileApp(mobileApp);
        cinemaDAO.save(cinema);
        assertEquals(mobileApp, mobileAppDAO.get(1));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(mobileAppDAO.get(1), cinemaDAO.getMobileApp("Высоцкого"));
    }

    @Test
    @DisplayName("Get all rows from mobileApp table")
    @Order(4)
    public void m4() {
        Cinema cinema = new Cinema();
        MobileApp mobileApp = new MobileApp();

        cinema.setAddress("Бочароваа 20");
        cinema.setCinemaName("Бочарова");
        cinema.setInfo("some info");

        mobileApp.setCinema(cinema);
        mobileApp.setInfo("some info 2");
        cinema.setMobileApp(mobileApp);
        cinemaDAO.save(cinema);

        assertEquals(2, mobileAppDAO.getAll().size());
    }


    @Test
    @DisplayName("Delete data from mobileApp table using Cinema and mobileApp")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaDAO.getAll().size());

        cinemaDAO.delete("Высоцкого");

        assertEquals(1, cinemaDAO.getAll().size());

        assertEquals(1, mobileAppDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));
    }

    @Test
    @DisplayName("update data in mobileApp table")
    @Order(6)
    public void m6() {
        MobileApp mobileApp = mobileAppDAO.get(2);

        mobileApp.setInfo("UPDATED info");
        mobileAppDAO.update(mobileApp);

        assertEquals(mobileApp, mobileAppDAO.get(2));

        mobileAppDAO.delete(2);

        assertNull(mobileAppDAO.get(1));

        assertNull(mobileAppDAO.get(3));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }
}