package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.StockDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Stock;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockTest {
    private static SessionFactory sessionFactory = null;
    private static StockDAO stockDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        stockDAO = new StockDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to stock table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Stock stock = new Stock();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        stock.setCinema(cinema);
        stock.setInfo("Акция №1 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №1");
        stockDAO.save(stock);

        assertEquals(stock, stockDAO.get("Акция №1"));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        Stock stock = new Stock();

        stock.setCinema(cinemaDAO.get("Высоцкого"));
        stock.setInfo("Акция №2 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №2");

        stockDAO.save(stock);

        assertEquals(Set.of(stockDAO.get("Акция №1"), stock), cinemaDAO.getStocks("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from stock table")
    @Order(4)
    public void m4() {
        assertEquals(2, stockDAO.getAll().size());
    }

    @Test
    @DisplayName("Delete data from stock table using Cinema and Stock")
    @Order(5)
    public void m5() {
        Stock stock = new Stock();
        Cinema cinema = cinemaDAO.get("Высоцкого");

        stock.setCinema(cinema);
        stock.setInfo("Акция №3 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №3");
        stockDAO.save(stock);

        assertEquals(3, stockDAO.getAll().size());

        cinemaDAO.delete(cinema.getCinemaName());

        assertEquals(0, cinemaDAO.getAll().size());

        assertEquals(0, stockDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));

        stockDAO.delete("Акция №1");

        assertEquals(0, stockDAO.getAll().size());
    }

    @Test
    @DisplayName("update data in stock table")
    @Order(6)
    public void m6() {
        Cinema cinema = new Cinema();
        Stock stock = new Stock();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        stock.setCinema(cinema);
        stock.setInfo("Акция №1 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №1");
        stockDAO.save(stock);

        stock.setTitle("UPDATED name");
        stockDAO.update(stock);

        assertEquals(stock, stockDAO.get("UPDATED name"));

        stockDAO.delete("UPDATED name");

        assertNull(stockDAO.get("UPDATED name"));

        assertNotNull(cinemaDAO.get("Высоцкого"));
    }


}
