package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Stock;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StockDao {
    private SessionFactory factory;

    public StockDao(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(Stock stock) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(stock);
            transaction.commit();
        }
    }

    public Stock get(long key) {
        try (final Session session = factory.openSession()) {
            return session.get(Stock.class, key);
        }
    }
}
