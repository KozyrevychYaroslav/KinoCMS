package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CafeBarDAO implements DAO<CafeBar> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(CafeBar cafeBar) {
        entityManager.persist(cafeBar);
    }

    @Override
    public void delete(CafeBar cafeBar) {
        try {
            entityManager.remove(cafeBar);
        } catch (IllegalArgumentException e) {
            System.out.println("No cafe: " + cafeBar + " in database ");
        }
    }

    @Override
    public void update(CafeBar cafeBar) {
        try {
            entityManager.merge(cafeBar);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update cafe bar");
        }
    }

    @Override
    public CafeBar get(long id) {
        return entityManager.find(CafeBar.class, id);
    }

    @Override
    public List<CafeBar> getAll() {
        return entityManager.createQuery("from CafeBar ", CafeBar.class).getResultList();
    }
}
