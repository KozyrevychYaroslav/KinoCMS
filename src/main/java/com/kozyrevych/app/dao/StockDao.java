package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Stock;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class StockDao {
    private final SessionFactory factory;

    public StockDao(SessionFactory factory) {
        this.factory = factory;
    }

    public void add(Stock stock) {
        try(Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(stock);
            session.getTransaction().commit();
        }
    }

    public Stock get(long key) {
        Stock stock;
        try(final Session session = factory.openSession()) {
            stock = session.get(Stock.class, key);
            if(stock != null) {
                Hibernate.initialize(stock.getCinema());
            }
            else
                stock = new Stock();
            return stock;
        }
    }
}
