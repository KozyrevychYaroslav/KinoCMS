package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

public class CurrentFilmDataDAO {
    private SessionFactory factory;

    public CurrentFilmDataDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(CurrentFilmData currentFilmData) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(currentFilmData);
            transaction.commit();
        }
    }

    public CurrentFilmData get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(CurrentFilmData.class, id);
        }
    }

    public void update(CurrentFilmData currentFilmData) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(currentFilmData);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update CurrentFilmData");
            }
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CurrentFilmData c = get(id);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No CurrentFilmData with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    public List<CurrentFilmData> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from CurrentFilmData", CurrentFilmData.class).getResultList();
        }

    }

    public Set<User> getUsers(long id) {
        try (final Session session = factory.openSession()) {
            CurrentFilmData c = session.get(CurrentFilmData.class, id);
            Hibernate.initialize(c.getUsers());
            return c.getUsers();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

}
