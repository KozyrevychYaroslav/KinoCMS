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

public class FilmHallDAO {
    private SessionFactory factory;

    public FilmHallDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(FilmHall filmHall) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(filmHall);
            transaction.commit();
        }
    }

    public void delete(int filmHallNumber) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            FilmHall c = get(filmHallNumber);
            try {
                session.delete(c);
            } catch(IllegalArgumentException e) {
                System.out.println("No FilmHall with number: " + filmHallNumber + " in database ");
            }
            transaction.commit();
        }
    }

    public void update(FilmHall filmHall) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(filmHall);
            } catch(IllegalArgumentException e) {
                System.out.println("Can't update FilmHall");
            }
            transaction.commit();
        }
    }

    public FilmHall get(int filmHallNumber) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select c from FilmHall c where filmHallNumber =: filmHallNumber");
            query.setParameter("filmHallNumber", filmHallNumber);
            try {
                return (FilmHall) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public List<FilmHall> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from FilmHall", FilmHall.class).getResultList();
        }
    }

    public Set<CurrentFilmData> getCurrentFilmDatas(long id) {
        try (final Session session = factory.openSession()) {
            FilmHall c = session.get(FilmHall.class, id);
            Hibernate.initialize(c.getCurrentFilmsData());
            return c.getCurrentFilmsData();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public Set<FreePlace> getFreePlaces(long id) {
        try (final Session session = factory.openSession()) {
            FilmHall c = session.get(FilmHall.class, id);
            Hibernate.initialize(c.getFreePlaces());
            return c.getFreePlaces();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

}
