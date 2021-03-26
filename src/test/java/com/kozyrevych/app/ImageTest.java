package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.ImageDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Image;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ImageTest {
    private static SessionFactory sessionFactory = null;
    private static ImageDAO imageDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        imageDAO = new ImageDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to image table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Image image = new Image();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        image.setCinema(cinema);
        image.setImageName("Image name 1");
        image.setImagePath("com/kozyrevych/app");
        imageDAO.save(image);

        assertEquals(image, imageDAO.get("Image name 1"));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        Image image = new Image();

        image.setCinema(cinemaDAO.get("Высоцкого"));
        image.setImageName("Image name 2");
        image.setImagePath("com/kozyrevych/app");
        imageDAO.save(image);

        assertEquals(Set.of(imageDAO.get("Image name 1"), image), cinemaDAO.getImages("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from Image table")
    @Order(4)
    public void m4() {
        assertEquals(2, imageDAO.getAll().size());
    }

    @Test
    @DisplayName("Delete data from image table using Cinema and Image")
    @Order(5)
    public void m5() {
        Image image = new Image();
        Cinema cinema = cinemaDAO.get("Высоцкого");

        image.setCinema(cinema);
        image.setImageName("Image name 3");
        image.setImagePath("com/kozyrevych/app");
        imageDAO.save(image);

        assertEquals(3, imageDAO.getAll().size());

        cinemaDAO.delete(cinema.getCinemaName());

        assertEquals(0, cinemaDAO.getAll().size());

        assertEquals(0, imageDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));

    }

    @Test
    @DisplayName("update data in image table")
    @Order(6)
    public void m6() {
        Cinema cinema = new Cinema();
        Image image = new Image();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        image.setCinema(cinema);
        image.setImageName("Image name 1");
        image.setImagePath("com/kozyrevych/app");
        imageDAO.save(image);

        image.setImageName("UPDATED name");
        imageDAO.update(image);

        assertEquals(image, imageDAO.get("UPDATED name"));

        imageDAO.delete("UPDATED name");

        assertNull(imageDAO.get("UPDATED name"));

        assertNotNull(cinemaDAO.get("Высоцкого"));
    }


}
