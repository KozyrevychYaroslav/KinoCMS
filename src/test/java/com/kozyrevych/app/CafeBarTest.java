package com.kozyrevych.app;

import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.services.CafeBarService;
import com.kozyrevych.app.services.CinemaService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CafeBarTest {
    @Autowired
    private CafeBarService cafeBarService = null;
    @Autowired
    private CinemaService cinemaService = null;

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
        cinemaService.save(cinema);

        assertEquals(cafeBar, cafeBarService.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(cafeBarService.get(1L), cinemaService.getCafeBar("Высоцкого"));
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
        cinemaService.save(cinema);

        assertEquals(2, cafeBarService.getAll().size());
    }


    @Test
    @DisplayName("Delete data from CafeBar table using Cinema and cafeBar")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaService.getAll().size());

        cinemaService.deleteByName("Высоцкого");

        assertEquals(1, cinemaService.getAll().size());

        assertEquals(1, cafeBarService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
    }

    @Test
    @DisplayName("update data in CafeBar table")
    @Order(6)
    public void m6() {
        CafeBar cafeBar = cafeBarService.get(2L);

        cafeBar.setInfo("UPDATED info");
        cafeBarService.update(cafeBar);

        assertEquals(cafeBar, cafeBarService.get(2L));

        cafeBarService.deleteById(2L);

        assertNull(cafeBarService.get(1L));

        assertNull(cafeBarService.get(3L));

        assertNotNull(cinemaService.getByName("Бочарова"));
    }
}