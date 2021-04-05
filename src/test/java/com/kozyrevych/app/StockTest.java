package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Stock;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.StockService;
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
    private StockService stockService = null;
    @Autowired
    private CinemaService cinemaService = null;


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
        cinemaService.save(cinema);

        assertEquals(stock, stockService.getByName("Акция №1"));
    }

    @Test
    @DisplayName("Checked one to many relationship")
    @Order(3)
    public void m2() {
        Stock stock = new Stock();

        stock.setCinema(cinemaService.getByName("Высоцкого"));
        stock.setInfo("Акция №2 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №2");

        stockService.save(stock);
        assertEquals(Set.of(stockService.getByName("Акция №1"), stock), cinemaService.getStocks("Высоцкого"));
    }


    @Test
    @DisplayName("Get all rows from stock table")
    @Order(4)
    public void m4() {
        assertEquals(2, stockService.getAll().size());
    }

    @Test
    @DisplayName("Delete data from stock table using Cinema and Stock")
    @Order(5)
    public void m5() {
        Stock stock = new Stock();
        Cinema cinema = cinemaService.getByName("Высоцкого");

        stock.setCinema(cinema);
        stock.setInfo("Акция №3 info");
        stock.setDate(LocalDate.now());
        stock.setTitle("Акция №3");
        stockService.save(stock);

        assertEquals(3, stockService.getAll().size());

        cinemaService.deleteByName(cinema.getCinemaName());

        assertEquals(0, cinemaService.getAll().size());

        assertEquals(0, stockService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
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
        cinemaService.save(cinema);

        stock.setTitle("UPDATED name");
        stockService.update(stock);

        assertEquals(stock, stockService.getByName("UPDATED name"));

        stockService.deleteByName("UPDATED name");

        assertNull(stockService.getByName("UPDATED name"));

        assertNotNull(cinemaService.getByName("Высоцкого"));
    }


}
