package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.CafeBar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CafeBarDAO {
    private SessionFactory factory;

    @Autowired
    public CafeBarDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(CafeBar cafeBar) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(cafeBar);
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CafeBar c = get(id);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No cafe bar with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    public void update(CafeBar cafeBar) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(cafeBar);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update cafe bar");
            }
            transaction.commit();
        }
    }

    public CafeBar get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(CafeBar.class, id);
        }
    }

    public List<CafeBar> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from CafeBar ", CafeBar.class).getResultList();
        }
    }
}
