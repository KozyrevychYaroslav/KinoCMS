package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FreePlace;
import com.kozyrevych.app.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

public class UserDAO {
    private SessionFactory factory;

    public UserDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(User User) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(User);
            transaction.commit();
        }
    }

    public User get(String phoneNumber) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("from User c where phoneNumber =: phoneNumber");
            query.setParameter("phoneNumber", phoneNumber);
            try {
                return (User) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public void update(User User) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(User);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update User");
            }
            transaction.commit();
        }
    }

    public void delete(String phoneNumber) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User c = get(phoneNumber);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No User with phoneNumber: " + phoneNumber + " in database ");
            }
            transaction.commit();
        }
    }

    public List<User> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }

    public Set<CurrentFilmData> getCurrentFilmDatas(String phoneNumber) {
        try (final Session session = factory.openSession()) {
            User c = get(phoneNumber);
            c = session.get(User.class, c.getId());
            Hibernate.initialize(c.getCurrentFilmsData());
            return c.getCurrentFilmsData();
        } catch (NullPointerException e) {
            System.out.println("No phoneNumber: " + phoneNumber);
            return null;
        }
    }

    public Set<FreePlace> getFreePlaces(String phoneNumber) {
        try (final Session session = factory.openSession()) {
            User c = get(phoneNumber);
            c = session.get(User.class, c.getId());
            Hibernate.initialize(c.getFreePlaces());
            return c.getFreePlaces();
        } catch (NullPointerException e) {
            System.out.println("No phoneNumber: " + phoneNumber);
            return null;
        }
    }

}
