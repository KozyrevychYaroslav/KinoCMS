package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertisingDAO {
    private SessionFactory factory;

    @Autowired
    public AdvertisingDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(Advertising advertising) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(advertising);
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Advertising c = get(id);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No advertising with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

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

    public Advertising get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Advertising.class, id);
        }
    }

    public List<Advertising> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Advertising", Advertising.class).getResultList();
        }
    }
}
