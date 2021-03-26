package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.NewsDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.News;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NewsTest {
    private static SessionFactory sessionFactory = null;
    private static NewsDAO newsDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        newsDAO = new NewsDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to news table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        News news = new News();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        news.setCinema(cinema);
        news.setInfo("новость №1 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №1");
        newsDAO.save(news);

        assertEquals(news, newsDAO.get("Новость №1"));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        News news = new News();

        news.setCinema(cinemaDAO.get("Высоцкого"));
        news.setInfo("Новость №2 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №2");

        newsDAO.save(news);

        assertEquals(Set.of(newsDAO.get("Новость №1"), news), cinemaDAO.getNews("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from news table")
    @Order(4)
    public void m4() {
        assertEquals(2, newsDAO.getAll().size());
    }

    @Test
    @DisplayName("Delete data from news table using Cinema and News")
    @Order(5)
    public void m5() {
        News news = new News();
        Cinema cinema = cinemaDAO.get("Высоцкого");

        news.setCinema(cinema);
        news.setInfo("Новость №3 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №3");
        newsDAO.save(news);

        assertEquals(3, newsDAO.getAll().size());

        cinemaDAO.delete(cinema.getCinemaName());

        assertEquals(0, cinemaDAO.getAll().size());

        assertEquals(0, newsDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));
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
        cinemaDAO.save(cinema);
        news.setCinema(cinema);
        news.setInfo("Новость №1 info");
        news.setDate(LocalDate.now());
        news.setNewsTitle("Новость №1");
        newsDAO.save(news);

        news.setNewsTitle("UPDATED name");
        newsDAO.update(news);

        assertEquals(news, newsDAO.get("UPDATED name"));

        newsDAO.delete("UPDATED name");

        assertNull(newsDAO.get("UPDATED name"));

        assertNotNull(cinemaDAO.get("Высоцкого"));
    }


}
