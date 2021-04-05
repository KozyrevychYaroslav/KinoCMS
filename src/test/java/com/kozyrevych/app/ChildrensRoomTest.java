package com.kozyrevych.app;

import com.kozyrevych.app.model.ChildrensRoom;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.services.ChildrensRoomService;
import com.kozyrevych.app.services.CinemaService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChildrensRoomTest {
    @Autowired
    private ChildrensRoomService childrensRoomService = null;
    @Autowired
    private CinemaService cinemaService = null;

    @Test
    @DisplayName("Add and get data to childrensRoom table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        ChildrensRoom childrensRoom = new ChildrensRoom();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        childrensRoom.setCinema(cinema);
        childrensRoom.setInfo("some info 1");
        childrensRoom.setCinema(cinema);
        cinema.setChildrensRoom(childrensRoom);
        cinemaService.save(cinema);

        assertEquals(childrensRoom, childrensRoomService.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(childrensRoomService.get(1L), cinemaService.getChildrensRoom("Высоцкого"));
    }

    @Test
    @DisplayName("Get all rows from childrensRoom table")
    @Order(4)
    public void m4() {
        Cinema cinema = new Cinema();
        ChildrensRoom childrensRoom = new ChildrensRoom();

        cinema.setAddress("Бочароваа 20");
        cinema.setCinemaName("Бочарова");
        cinema.setInfo("some info");
        childrensRoom.setCinema(cinema);
        childrensRoom.setInfo("some info 2");
        childrensRoom.setCinema(cinema);
        cinema.setChildrensRoom(childrensRoom);
        cinemaService.save(cinema);

        assertEquals(2, childrensRoomService.getAll().size());
    }


    @Test
    @DisplayName("Delete data from childrensRoom table using Cinema and childrensRoom")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaService.getAll().size());

        cinemaService.deleteByName("Высоцкого");

        assertEquals(1, cinemaService.getAll().size());

        assertEquals(1, childrensRoomService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
    }

    @Test
    @DisplayName("update data in childrensRoom table")
    @Order(6)
    public void m6() {
        ChildrensRoom childrensRoom = childrensRoomService.get(2L);

        childrensRoom.setInfo("UPDATED info");
        childrensRoomService.update(childrensRoom);

        assertEquals(childrensRoom, childrensRoomService.get(2L));

        childrensRoomService.deleteById(2L);

        assertNull(childrensRoomService.get(1L));

        assertNull(childrensRoomService.get(3L));

        assertNotNull(cinemaService.getByName("Бочарова"));
    }
}