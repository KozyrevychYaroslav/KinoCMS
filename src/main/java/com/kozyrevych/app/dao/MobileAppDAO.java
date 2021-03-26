package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.MobileApp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MobileAppDAO {
    private SessionFactory factory;

    public MobileAppDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(MobileApp MobileApp) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(MobileApp);
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            MobileApp c = get(id);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No MobileApp with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    public void update(MobileApp mobileApp) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(mobileApp);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update MobileApp");
            }
            transaction.commit();
        }
    }

    public MobileApp get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(MobileApp.class, id);
        }
    }

    public List<MobileApp> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from MobileApp ", MobileApp.class).getResultList();
        }
    }
}
