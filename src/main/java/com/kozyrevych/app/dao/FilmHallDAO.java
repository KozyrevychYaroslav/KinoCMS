package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FilmHall;
import com.kozyrevych.app.model.FreePlace;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Component
public class FilmHallDAO implements DAO<FilmHall> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(FilmHall filmHall) {
        entityManager.persist(filmHall);

    }

    @Override
    public void delete(FilmHall filmHall) {
        try {
            entityManager.remove(filmHall);
        } catch (IllegalArgumentException e) {
            System.out.println("No FilmHall: " + filmHall + " in database ");
        }
    }

    @Override
    public void update(FilmHall filmHall) {
        try {
            entityManager.merge(filmHall);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update FilmHall");
        }

    }

    @Override
    public FilmHall get(long id) {
        try {
            return entityManager.find(FilmHall.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public FilmHall getByFilmHallNumber(int filmHallNumber) {
        try {
            return (FilmHall) entityManager.
                    createQuery("select c from FilmHall c where filmHallNumber =: filmHallNumber").
                    setParameter("filmHallNumber", filmHallNumber).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<FilmHall> getAll() {
        return entityManager.createQuery("from FilmHall", FilmHall.class).getResultList();
    }

}
