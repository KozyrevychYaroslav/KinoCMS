package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Image;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.ImageService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageTest {
    @Autowired
    private ImageService imageService = null;
    @Autowired
    private CinemaService cinemaService = null;


    @Test
    @DisplayName("Add and get data to image table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Image image = new Image();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        image.setCinema(cinema);
        image.setImageName("Image name 1");
        image.setImagePath("com/kozyrevych/app");
        cinema.setImages(Collections.singleton(image));
        cinemaService.save(cinema);

        assertEquals(image, imageService.getByName("Image name 1"));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        Image image = new Image();

        image.setCinema(cinemaService.getByName("Высоцкого"));
        image.setImageName("Image name 2");
        image.setImagePath("com/kozyrevych/app");
        imageService.save(image);

        assertEquals(Set.of(imageService.getByName("Image name 1"), image), cinemaService.getImages("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from Image table")
    @Order(4)
    public void m4() {
        assertEquals(2, imageService.getAll().size());
    }

    @Test
    @DisplayName("Delete data from image table using Cinema and Image")
    @Order(5)
    public void m5() {
        Image image = new Image();
        Cinema cinema = cinemaService.getByName("Высоцкого");

        image.setCinema(cinema);
        image.setImageName("Image name 3");
        image.setImagePath("com/kozyrevych/app");
        imageService.save(image);

        assertEquals(3, imageService.getAll().size());

        cinemaService.deleteByName(cinema.getCinemaName());

        assertEquals(0, cinemaService.getAll().size());

        assertEquals(0, imageService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));

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

        image.setCinema(cinema);
        image.setImageName("Image name 1");
        image.setImagePath("com/kozyrevych/app");
        cinema.setImages(Collections.singleton(image));
        cinemaService.save(cinema);

        image.setImageName("UPDATED name");
        imageService.update(image);

        assertEquals(image, imageService.getByName("UPDATED name"));

        imageService.deleteByName("UPDATED name");

        assertNull(imageService.getByName("UPDATED name"));

        assertNotNull(cinemaService.getByName("Высоцкого"));
    }


}
