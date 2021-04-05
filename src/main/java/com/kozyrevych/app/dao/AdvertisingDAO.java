package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class AdvertisingDAO implements DAO<Advertising> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(Advertising advertising) {
        entityManager.persist(advertising);
    }

    @Override
    public void delete(Advertising advertising) {
        try {
            entityManager.remove(advertising);
        } catch (IllegalArgumentException e) {
            System.out.println("No advertising: " + advertising);
        }
    }

    @Override
    public void update(Advertising advertising) {
        try {
            entityManager.merge(advertising);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update cafe bar");
        }
    }

    @Override
    public Advertising get(long id) {
        return entityManager.find(Advertising.class, id);
    }

    @Override
    public List<Advertising> getAll() {
        return entityManager.createQuery("from Advertising", Advertising.class).getResultList();
    }
}
