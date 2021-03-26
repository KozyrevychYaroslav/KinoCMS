package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class AdvertisingDAO {
    private SessionFactory factory;

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
            } catch(IllegalArgumentException e) {
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
            } catch(IllegalArgumentException e) {
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
