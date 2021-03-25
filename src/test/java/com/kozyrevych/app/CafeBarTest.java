package com.kozyrevych.app;

import com.kozyrevych.app.dao.CafeBarDAO;
import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.Cinema;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CafeBarTest {
    private static SessionFactory sessionFactory = null;
    private static CafeBarDAO cafeBarDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        cafeBarDAO = new CafeBarDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to CafeBar table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        CafeBar cafeBar = new CafeBar();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        cafeBar.setCinema(cinema);
        cafeBar.setInfo("some info 1");
        cafeBarDAO.save(cafeBar);

        assertEquals(cafeBar, cafeBarDAO.get(1));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(cafeBarDAO.get(1), cinemaDAO.getCafeBar(1));
    }

    @Test
    @DisplayName("Get all rows from cafe bar table")
    @Order(4)
    public void m4() {
        Cinema cinema = new Cinema();
        CafeBar cafeBar = new CafeBar();

        cinema.setAddress("Бочароваа 20");
        cinema.setCinemaName("Бочарова");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        cafeBar.setCinema(cinema);
        cafeBar.setInfo("some info 2");
        cafeBarDAO.save(cafeBar);

        assertEquals(2, cafeBarDAO.getAll().size());
    }


    @Test
    @DisplayName("Delete data from CafeBar table using Cinema and cafe")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaDAO.getAll().size());

        cinemaDAO.delete("Высоцкого");

        assertEquals(1, cinemaDAO.getAll().size());

        assertEquals(1, cafeBarDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));
    }

    @Test
    @DisplayName("update data in CafeBar table")
    @Order(6)
    public void m6() {
        CafeBar cafeBar = cafeBarDAO.get(2);

        cafeBar.setInfo("UPDATED info");
        cafeBarDAO.update(cafeBar);

        assertEquals(cafeBar, cafeBarDAO.get(2));

        cafeBarDAO.delete(2);

        assertNull(cafeBarDAO.get(1));

        assertNull(cafeBarDAO.get(3));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }


}
