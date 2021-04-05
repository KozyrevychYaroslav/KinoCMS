package com.kozyrevych.app;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.services.AdvertisingService;
import com.kozyrevych.app.services.CinemaService;
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
    private AdvertisingService advertisingService;

    @Autowired
    private CinemaService cinemaService;

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
        cinemaService.save(cinema);

        assertEquals(advertising, advertisingService.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(advertisingService.get(1L), cinemaService.getAdvertising("Высоцкого"));
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
        cinemaService.save(cinema);

        assertEquals(2, advertisingService.getAll().size());
    }


    @Test
    @DisplayName("Delete data from Advertising table using Cinema and Advertising")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaService.getAll().size());

        cinemaService.deleteByName("Высоцкого");

        assertEquals(1, cinemaService.getAll().size());

        assertEquals(1, advertisingService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
    }

    @Test
    @DisplayName("update data in Advertising table")
    @Order(6)
    public void m6() {
        Advertising advertising = advertisingService.get(2L);

        advertising.setInfo("UPDATED info");
        advertisingService.update(advertising);

        assertEquals(advertising, advertisingService.get(2L));

        advertisingService.deleteById(2L);

        assertNull(advertisingService.get(1L));

        assertNull(advertisingService.get(3L));

        assertNotNull(cinemaService.getByName("Бочарова"));
    }
}