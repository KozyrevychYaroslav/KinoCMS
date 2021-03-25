package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Stock;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class StockDAO {
    private SessionFactory factory;

    public StockDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(Stock stock) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(stock);
            transaction.commit();
        }
    }

    public void delete(String name) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Stock c = get(name);
            try {
                session.delete(c);
            } catch(IllegalArgumentException e) {
                System.out.println("No stock name: " + name + " in database ");
            }
            transaction.commit();
        }
    }

    public void update(Stock stock) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(stock);
            } catch(IllegalArgumentException e) {
                System.out.println("Can't update stock");
            }
            transaction.commit();
        }
    }

    public Stock get(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select c from Stock c where title =: name");
            query.setParameter("name", name);
            try {
                return (Stock) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public List<Stock> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Stock", Stock.class).getResultList();
        }
    }
}
