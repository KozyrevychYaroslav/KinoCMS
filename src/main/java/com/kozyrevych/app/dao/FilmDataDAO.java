package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FilmData;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Component
public class FilmDataDAO implements DAO<FilmData> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(FilmData filmData) {
        entityManager.unwrap(Session.class).save(filmData);
    }

    @Override
    public FilmData get(long id) {
        try {
            return entityManager.find(FilmData.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public FilmData getByTitle(String title) {
        try {
            return (FilmData)entityManager.
                    createQuery("from FilmData c where filmTitle =: title").
                    setParameter("title", title).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(FilmData filmData) {
        try {
            entityManager.merge(filmData);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update filmData");
        }
    }

    @Override
    public void delete(FilmData filmData) {
        try {
            entityManager.remove(filmData);
        } catch (IllegalArgumentException e) {
            System.out.println("No FilmData with filmData: " + filmData + " in database ");
        }
    }

    @Override
    public List<FilmData> getAll() {
        return entityManager.createQuery("from FilmData", FilmData.class).getResultList();

    }
}
