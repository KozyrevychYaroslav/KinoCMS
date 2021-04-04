package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.Stock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;

@Component
public class StockDAO implements DAO<Stock, String> {
    private SessionFactory factory;

    @Autowired
    public StockDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Stock stock) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(stock);
            transaction.commit();
        }
    }

    @Override
    public void delete(String name) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Stock c = get(name);
            c = (Stock) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No stock name: " + name + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
    public void update(Stock stock) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(stock);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update stock");
            }
            transaction.commit();
        }
    }

    @Override
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

    @Override
    public List<Stock> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Stock", Stock.class).getResultList();
        }
    }
}
