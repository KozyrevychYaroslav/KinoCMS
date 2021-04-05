package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Component
public class CinemaDAO implements DAO<Cinema> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(Cinema cinema) {
        entityManager.unwrap(Session.class).save(cinema);;

    }

    @Override
    public Cinema get(long id) {
        try {
            return entityManager.find(Cinema.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cinema getByName(String Name) {
        try {
            return (Cinema) entityManager.
                    createQuery("select c from Cinema c where cinemaName =: Name").
                    setParameter("Name", Name).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(Cinema cinema) {
        try {
            entityManager.merge(cinema);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update cinema");
        }
    }

    @Override
    public void delete(Cinema cinema) {
        try {
            entityManager.remove(cinema);
        } catch (IllegalArgumentException e) {
            System.out.println("No cinema: " + cinema+ " in database ");
        }
    }

    @Override
    public List<Cinema> getAll() {
        try {
            return entityManager.createQuery("from Cinema", Cinema.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    public List getCurrentFilmsData(String name) {
        try {
            return entityManager.
                    createQuery("select CFD from CurrentFilmData CFD join CFD.filmHall FH" +
                            " join FH.cinema C where C.cinemaName =: name").setParameter("name", name).
                    getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List getFilmsData(String name) {
        try {
            return entityManager.createQuery("select fd from Cinema c " +
                    "join c.filmHalls fh " +
                    "join  fh.currentFilmsData cfd " +
                    "join cfd.filmData fd " +
                    "where c.cinemaName =: name " +
                    "group by fd.id").setParameter("name", name).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List getNumberOfFilmData(String name) {
        try {
            return entityManager.createQuery("select count(*) from Cinema c " +
                    "join c.filmHalls fh " +
                    "join  fh.currentFilmsData cfd " +
                    "join cfd.filmData fd " +
                    "where c.cinemaName =: name " +
                    "group by fd.id ").setParameter("name", name).getResultList();
        } catch (NullPointerException | NoResultException e) {
        System.out.println("no cinema: " + name);
        return null;
    }

    }
}
