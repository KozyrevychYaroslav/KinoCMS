package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CafeBarDAO implements DAO<CafeBar, Long> {
    private SessionFactory factory;

    @Autowired
    public CafeBarDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(CafeBar cafeBar) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(cafeBar);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CafeBar c = get(id);
            c = (CafeBar) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No cafe bar with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
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

    @Override
    public CafeBar get(Long id) {
        try (final Session session = factory.openSession()) {
            return session.get(CafeBar.class, id);
        }
    }

    @Override
    public List<CafeBar> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from CafeBar ", CafeBar.class).getResultList();
        }
    }
}
