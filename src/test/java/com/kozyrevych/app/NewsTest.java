package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.News;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.NewsService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NewsTest {
    @Autowired
    private NewsService newsService = null;
    @Autowired
    private CinemaService cinemaService = null;

    @Test
    @DisplayName("Add and get data to news table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        News news = new News();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        news.setCinema(cinema);
        news.setInfo("новость №1 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №1");
        cinema.setNews(Collections.singleton(news));
        cinemaService.save(cinema);

        assertEquals(news, newsService.getByName("Новость №1"));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        News news = new News();

        news.setCinema(cinemaService.getByName("Высоцкого"));
        news.setInfo("Новость №2 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №2");

        newsService.save(news);

        assertEquals(Set.of(newsService.getByName("Новость №1"), news), cinemaService.getNews("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from news table")
    @Order(4)
    public void m4() {
        assertEquals(2, newsService.getAll().size());
    }

    @Test
    @DisplayName("Delete data from news table using Cinema and News")
    @Order(5)
    public void m5() {
        News news = new News();
        Cinema cinema = cinemaService.getByName("Высоцкого");

        news.setCinema(cinema);
        news.setInfo("Новость №3 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №3");
        newsService.save(news);

        assertEquals(3, newsService.getAll().size());

        cinemaService.deleteByName(cinema.getCinemaName());

        assertEquals(0, cinemaService.getAll().size());

        assertEquals(0, newsService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
    }

    @Test
    @DisplayName("update data in news table")
    @Order(6)
    public void m6() {
        Cinema cinema = new Cinema();
        News news = new News();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        news.setCinema(cinema);
        news.setInfo("Новость №1 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №1");
        cinema.setNews(Collections.singleton(news));
        cinemaService.save(cinema);

        news.setNewsTitle("UPDATED name");
        newsService.update(news);

        assertEquals(news, newsService.getByName("UPDATED name"));

        newsService.deleteByName("UPDATED name");

        assertNull(newsService.getByName("UPDATED name"));

        assertNotNull(cinemaService.getByName("Высоцкого"));
    }


}
