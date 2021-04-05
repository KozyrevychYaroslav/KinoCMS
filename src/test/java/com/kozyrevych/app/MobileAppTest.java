package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.MobileApp;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.MobilleAppService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MobileAppTest {
    @Autowired
    private MobilleAppService mobileAppService = null;
    @Autowired
    private CinemaService cinemaService = null;

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
        cinemaService.save(cinema);
        assertEquals(mobileApp, mobileAppService.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(mobileAppService.get(1L), cinemaService.getMobileApp("Высоцкого"));
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
        cinemaService.save(cinema);

        assertEquals(2, mobileAppService.getAll().size());
    }


    @Test
    @DisplayName("Delete data from mobileApp table using Cinema and mobileApp")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaService.getAll().size());

        cinemaService.deleteByName("Высоцкого");

        assertEquals(1, cinemaService.getAll().size());

        assertEquals(1, mobileAppService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
    }

    @Test
    @DisplayName("update data in mobileApp table")
    @Order(6)
    public void m6() {
        MobileApp mobileApp = mobileAppService.get(2L);

        mobileApp.setInfo("UPDATED info");
        mobileAppService.update(mobileApp);

        assertEquals(mobileApp, mobileAppService.get(2L));

        mobileAppService.deleteById(2L);

        assertNull(mobileAppService.get(1L));

        assertNull(mobileAppService.get(3L));

        assertNotNull(cinemaService.getByName("Бочарова"));
    }
}