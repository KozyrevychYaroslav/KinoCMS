package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.User;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Component
public class CurrentFilmDataDAO implements DAO<CurrentFilmData> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(CurrentFilmData currentFilmData) {
        entityManager.unwrap(Session.class).save(currentFilmData);;

    }

    @Override
    public CurrentFilmData get(long id) {
        return entityManager.find(CurrentFilmData.class, id);
    }

    @Override
    public void update(CurrentFilmData currentFilmData) {
        try {
            entityManager.merge(currentFilmData);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update CurrentFilmData");
        }
    }

    @Override
    public void delete(CurrentFilmData currentFilmData) {
        try {
            entityManager.remove(currentFilmData);
        } catch (IllegalArgumentException e) {
            System.out.println("No CurrentFilmData: " + currentFilmData + " in database ");
        }
    }

    @Override
    public List<CurrentFilmData> getAll() {
        return entityManager.createQuery("from CurrentFilmData", CurrentFilmData.class).getResultList();
    }

    public long getNumberOfCurrentFilmData(long id ) {
        try {
            return  (Long) entityManager.
                    createQuery("select count(*) from CurrentFilmData cfd " +
                            "join cfd.users u " +
                            "where cfd.id =: id group by cfd.id").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

}
