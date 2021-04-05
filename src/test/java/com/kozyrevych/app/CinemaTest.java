package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.services.CinemaService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CinemaTest {
    @Autowired
    private CinemaService cinemaService = null;

    @Test
    @DisplayName("Adding data to cinema table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaService.save(cinema);
    }

    @Test
    @DisplayName("Get data from cinema table")
    @Order(3)
    public void m2() {
        assertAll(() -> assertEquals("Высоцкого", cinemaService.getByName("Высоцкого").getCinemaName()),
                () -> assertEquals("Высоцкого 50/б", cinemaService.getByName("Высоцкого").getAddress()),
                () -> assertEquals("some info", cinemaService.getByName("Высоцкого").getInfo())
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
        cinemaService.save(cinema);

        assertAll(() -> assertEquals("Высоцкого", cinemaService.getByName("Высоцкого").getCinemaName()),
                () -> assertEquals("Высоцкого 50/б", cinemaService.getByName("Высоцкого").getAddress()),
                () -> assertEquals("some info", cinemaService.getByName("Высоцкого").getInfo())
        );

        assertAll(() -> assertEquals("Аркадия", cinemaService.getByName("Аркадия").getCinemaName()),
                () -> assertEquals("Аркадия 60/а", cinemaService.getByName("Аркадия").getAddress()),
                () -> assertEquals("some info", cinemaService.getByName("Аркадия").getInfo())
        );

        assertEquals(2, cinemaService.getAll().size());

    }

    @Test
    @DisplayName("Delete data from cinema table")
    @Order(5)
    public void m4() {
        Cinema cinema = new Cinema();

        cinema.setAddress("delete");
        cinema.setCinemaName("delete");
        cinema.setInfo("delete");
        cinemaService.save(cinema);

        assertEquals(3, cinemaService.getAll().size());

        cinemaService.deleteByName("delete");

        assertEquals(2, cinemaService.getAll().size());

        assertNull(cinemaService.getByName("delete"));
    }

    @Order(6)
    @DisplayName("Update cinema")
    @Test
    public void m5() {
        Cinema cinema = new Cinema();

        cinema.setAddress("Аркадия 60/а");
        cinema.setCinemaName("Аркадия");
        cinema.setInfo("some info changed");

        Cinema cinema1 = cinemaService.getByName("Аркадия");

        cinema1.setInfo("some info changed");
        cinemaService.update(cinema1);

        assertEquals(cinema, cinemaService.getByName("Аркадия"));
    }
}
