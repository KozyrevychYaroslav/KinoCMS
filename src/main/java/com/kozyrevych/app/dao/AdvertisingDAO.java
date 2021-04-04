package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertisingDAO implements DAO<Advertising, Long> {
    private SessionFactory factory;

    @Autowired
    public AdvertisingDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Advertising advertising) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(advertising);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Advertising c = get(id);
            c = (Advertising) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No advertising with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
    public void update(Advertising advertising) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(advertising);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update cafe bar");
            }
            transaction.commit();
        }
    }

    @Override
    public Advertising get(Long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Advertising.class, id);
        }
    }

    @Override
    public List<Advertising> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Advertising", Advertising.class).getResultList();
        }
    }
}
