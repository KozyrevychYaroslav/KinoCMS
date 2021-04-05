package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.MobileApp;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class MobileAppDAO implements DAO<MobileApp> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(MobileApp MobileApp) {
        entityManager.persist(MobileApp);
    }

    @Override
    public void delete(MobileApp mobileApp) {
        try {
            entityManager.remove(mobileApp);
        } catch (IllegalArgumentException e) {
            System.out.println("No MobileApp: " + mobileApp + " in database ");
        }
    }

    @Override
    public void update(MobileApp mobileApp) {
        try {
            entityManager.merge(mobileApp);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update MobileApp");
        }
    }

    @Override
    public MobileApp get(long id) {
        return entityManager.find(MobileApp.class, id);
    }


    @Override
    public List<MobileApp> getAll() {
        return entityManager.createQuery("from MobileApp ", MobileApp.class).getResultList();
    }
}
