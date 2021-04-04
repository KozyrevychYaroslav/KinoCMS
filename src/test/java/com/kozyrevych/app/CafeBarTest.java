package com.kozyrevych.app;

import com.kozyrevych.app.dao.CafeBarDAO;
import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.Cinema;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CafeBarTest {
    @Autowired
    private CafeBarDAO cafeBarDAO = null;
    @Autowired
    private CinemaDAO cinemaDAO = null;

    @Test
    @DisplayName("Add and get data to CafeBar table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        CafeBar cafeBar = new CafeBar();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cafeBar.setCinema(cinema);
        cafeBar.setInfo("some info 1");
        cafeBar.setCinema(cinema);
        cinema.setCafeBar(cafeBar);
        cinemaDAO.save(cinema);

        assertEquals(cafeBar, cafeBarDAO.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(cafeBarDAO.get(1L), cinemaDAO.getCafeBar("Высоцкого"));
    }

    @Test
    @DisplayName("Get all rows from cafe bar table")
    @Order(4)
    public void m4() {
        Cinema cinema = new Cinema();
        CafeBar cafeBar = new CafeBar();

        cafeBar.setCinema(cinema);
        cafeBar.setInfo("some info 2");
        cinema.setAddress("Бочароваа 20");
        cinema.setCinemaName("Бочарова");
        cinema.setInfo("some info");
        cinema.setCafeBar(cafeBar);
        cinemaDAO.save(cinema);

        assertEquals(2, cafeBarDAO.getAll().size());
    }


    @Test
    @DisplayName("Delete data from CafeBar table using Cinema and cafeBar")
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
        CafeBar cafeBar = cafeBarDAO.get(2L);

        cafeBar.setInfo("UPDATED info");
        cafeBarDAO.update(cafeBar);

        assertEquals(cafeBar, cafeBarDAO.get(2L));

        cafeBarDAO.delete(2L);

        assertNull(cafeBarDAO.get(1L));

        assertNull(cafeBarDAO.get(3L));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }
}