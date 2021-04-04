package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class UserDAO implements DAO<User, String>{
    private SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        try (final Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    @Override
    public User get(String phoneNumber) {
        try (final Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from User c where phoneNumber =: phoneNumber");
            query.setParameter("phoneNumber", phoneNumber);
            try {
                return (User) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    @Override
    public void update(User user) {
        try (final Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(user);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update User");
            }
            transaction.commit();
        }
    }

    @Override
    public void delete(String phoneNumber) {
        try (final Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User c = get(phoneNumber);
            c = (User) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No User with phoneNumber: " + phoneNumber + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
    public List<User> getAll() {
        try (final Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }

    public Set<CurrentFilmData> getCurrentFilmDatas(String phoneNumber) {
        try (final Session session = sessionFactory.openSession()) {
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
        try (final Session session = sessionFactory.openSession()) {
            User c = get(phoneNumber);
            c = session.get(User.class, c.getId());
            Hibernate.initialize(c.getFreePlaces());
            return c.getFreePlaces();
        } catch (NullPointerException e) {
            System.out.println("No phoneNumber: " + phoneNumber);
            return null;
        }
    }

    public Map<Gender, Long> getNumberOfGenders() {
        try (final Session session = sessionFactory.openSession()) {
            List<User> users = getAll();
            Map<Gender, Long> genders = new LinkedHashMap<>();
            genders.put(Gender.FEMALE, users.stream().filter(i -> i.getGender() == Gender.FEMALE).count());
            genders.put(Gender.MALE, users.stream().filter(i -> i.getGender() == Gender.MALE).count());
            return genders;
        }
    }

}
