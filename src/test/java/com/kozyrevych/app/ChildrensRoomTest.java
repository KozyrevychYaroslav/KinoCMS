package com.kozyrevych.app;

import com.kozyrevych.app.dao.ChildrensRoomDAO;
import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.model.ChildrensRoom;
import com.kozyrevych.app.model.Cinema;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ChildrensRoomTest {
    private static SessionFactory sessionFactory = null;
    private static ChildrensRoomDAO childrensRoomDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        childrensRoomDAO = new ChildrensRoomDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

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
        cinemaDAO.save(cinema);

        assertEquals(childrensRoom, childrensRoomDAO.get(1));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(childrensRoomDAO.get(1), cinemaDAO.getChildrensRoom("Высоцкого"));
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
        cinemaDAO.save(cinema);

        assertEquals(2, childrensRoomDAO.getAll().size());
    }


    @Test
    @DisplayName("Delete data from childrensRoom table using Cinema and childrensRoom")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaDAO.getAll().size());

        cinemaDAO.delete("Высоцкого");

        assertEquals(1, cinemaDAO.getAll().size());

        assertEquals(1, childrensRoomDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));
    }

    @Test
    @DisplayName("update data in childrensRoom table")
    @Order(6)
    public void m6() {
        ChildrensRoom childrensRoom = childrensRoomDAO.get(2);

        childrensRoom.setInfo("UPDATED info");
        childrensRoomDAO.update(childrensRoom);

        assertEquals(childrensRoom, childrensRoomDAO.get(2));

        childrensRoomDAO.delete(2);

        assertNull(childrensRoomDAO.get(1));

        assertNull(childrensRoomDAO.get(3));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }
}