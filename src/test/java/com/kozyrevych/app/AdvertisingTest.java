package com.kozyrevych.app;

import com.kozyrevych.app.dao.AdvertisingDAO;
import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.Cinema;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdvertisingTest {
    @Autowired
    private AdvertisingDAO advertisingDAO;
    @Autowired
    private CinemaDAO cinemaDAO;

    @Test
    @DisplayName("Add and get data to Advertising table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Advertising advertising = new Advertising();

        advertising.setInfo("some info 1");
        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        advertising.setCinema(cinema);
        cinema.setAdvertising(advertising);
        cinemaDAO.save(cinema);

        assertEquals(advertising, advertisingDAO.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(advertisingDAO.get(1L), cinemaDAO.getAdvertising("Высоцкого"));
    }

    @Test
    @DisplayName("Get all rows from Advertising table")
    @Order(4)
    public void m4() {
        Cinema cinema = new Cinema();
        Advertising advertising = new Advertising();

        advertising.setInfo("some info 2");
        cinema.setAddress("Бочароваа 20");
        cinema.setCinemaName("Бочарова");
        cinema.setInfo("some info");
        cinema.setAdvertising(advertising);
        advertising.setCinema(cinema);
        cinemaDAO.save(cinema);

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
        Advertising advertising = advertisingDAO.get(2L);

        advertising.setInfo("UPDATED info");
        advertisingDAO.update(advertising);

        assertEquals(advertising, advertisingDAO.get(2L));

        advertisingDAO.delete(2L);

        assertNull(advertisingDAO.get(1L));

        assertNull(advertisingDAO.get(3L));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }
}