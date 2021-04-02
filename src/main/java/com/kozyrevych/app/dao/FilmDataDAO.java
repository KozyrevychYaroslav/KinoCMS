package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FilmData;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmDataDAO {
    private SessionFactory factory;

    @Autowired
    public FilmDataDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(FilmData filmData) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(filmData);
            transaction.commit();
        }
    }

    public FilmData get(String title) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("from FilmData c where filmTitle =: title");
            query.setParameter("title", title);
            try {
                return (FilmData) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public void update(FilmData filmData) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(filmData);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update filmData");
            }
            transaction.commit();
        }
    }

    public void delete(String title) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            FilmData c = get(title);
            c = session.get(FilmData.class, c.getId());

            // так как FilmData является главной таблицей в связи manyToMany, а мы решили удалить элемент из нее,
            // то сначала надо очистить все ссылки на нее из таблицы Cinema
            c.removeFilmDataFromCinemas();
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No FilmData with title: " + title + " in database ");
                transaction.rollback();
            }
            transaction.commit();
        }
    }

    public List<FilmData> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from FilmData", FilmData.class).getResultList();
        }

    }

    public Set<Cinema> getCinemas(long id) {
        try (final Session session = factory.openSession()) {
            FilmData filmData = session.get(FilmData.class, id);
            Hibernate.initialize(filmData.getCinemas());
            return filmData.getCinemas();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public Set<CurrentFilmData> getCurrentFilmDatas(long id) {
        try (final Session session = factory.openSession()) {
            FilmData c = session.get(FilmData.class, id);
            Hibernate.initialize(c.getCurrentFilmData());
            return c.getCurrentFilmData();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

}
