package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FreePlace;
import com.kozyrevych.app.model.Gender;
import com.kozyrevych.app.model.User;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class UserDAO implements DAO<User> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.unwrap(Session.class).save(user);
    }

    @Override
    public User get(long id) {
        try {
            return entityManager.find(User.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public User getByPhoneNumber(String phoneNumber) {
        try {
            return (User) entityManager.
                    createQuery("from User c where phoneNumber =: phoneNumber").
                    setParameter("phoneNumber", phoneNumber).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(User user) {
        try {
            entityManager.merge(user);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update User");
        }
    }

    @Override
    public void delete(User user) {
        try {
            entityManager.remove(user);
        } catch (IllegalArgumentException e) {
            System.out.println("No User: " + user + " in database ");
        }

    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

}
