package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.MobileApp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MobileAppDAO implements DAO<MobileApp, Long>{
    private SessionFactory factory;

    @Autowired
    public MobileAppDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(MobileApp MobileApp) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(MobileApp);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            MobileApp c = get(id);
            c = (MobileApp) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No MobileApp with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
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

    @Override
    public MobileApp get(Long id) {
        try (final Session session = factory.openSession()) {
            return session.get(MobileApp.class, id);
        }
    }

    @Override
    public List<MobileApp> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from MobileApp ", MobileApp.class).getResultList();
        }
    }
}
