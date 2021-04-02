package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.FreePlace;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FreePlaceDAO {
    private SessionFactory factory;

    @Autowired
    public FreePlaceDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(FreePlace FreePlace) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(FreePlace);
            transaction.commit();
        }
    }

    public FreePlace get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(FreePlace.class, id);
        }
    }

    public void update(FreePlace FreePlace) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(FreePlace);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update FreePlace");
            }
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            FreePlace c = get(id);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No FreePlace with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    public List<FreePlace> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from FreePlace", FreePlace.class).getResultList();
        }

    }
}
