package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.FreePlace;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class FreePlaceDAO implements DAO<FreePlace> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(FreePlace FreePlace) {
        entityManager.persist(FreePlace);
    }

    @Override
    public FreePlace get(long id) {
        return entityManager.find(FreePlace.class, id);
    }


    @Override
    public void update(FreePlace freePlace) {
        try {
            entityManager.merge(freePlace);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update FreePlace");
        }
    }

    @Override
    public void delete(FreePlace freePlace) {
        try {
            entityManager.remove(freePlace);
        } catch (IllegalArgumentException e) {
            System.out.println("No FreePlace: " + freePlace + " in database ");
        }
    }

    @Override
    public List<FreePlace> getAll() {
        return entityManager.createQuery("from FreePlace", FreePlace.class).getResultList();
    }
}
