package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.StockDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Stock;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StockTest {
    @Autowired
    private StockDAO stockDAO = null;
    @Autowired
    private CinemaDAO cinemaDAO = null;


    @Test
    @DisplayName("Add and get data to stock table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Stock stock = new Stock();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");

        stock.setCinema(cinema);
        stock.setInfo("Акция №1 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №1");
        cinema.setStocks(Collections.singleton(stock));
        cinemaDAO.save(cinema);

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
        stock.setCinema(cinema);
        stock.setInfo("Акция №1 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №1");
        cinema.setStocks(Collections.singleton(stock));
        cinemaDAO.save(cinema);

        stock.setTitle("UPDATED name");
        stockDAO.update(stock);

        assertEquals(stock, stockDAO.get("UPDATED name"));

        stockDAO.delete("UPDATED name");

        assertNull(stockDAO.get("UPDATED name"));

        assertNotNull(cinemaDAO.get("Высоцкого"));
    }


}
