package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CurrentFilmDataDAO {
    private SessionFactory factory;

    @Autowired
    public CurrentFilmDataDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(CurrentFilmData currentFilmData) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(currentFilmData);
            transaction.commit();
        }
    }

    public CurrentFilmData get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(CurrentFilmData.class, id);
        }
    }

    public void update(CurrentFilmData currentFilmData) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(currentFilmData);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update CurrentFilmData");
            }
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CurrentFilmData c = session.get(CurrentFilmData.class, id);

            // так как currentFilmData является главной таблицей в связи manyToMany, а мы решили удалить элемент из нее,
            // то сначала надо очистить все ссылки на нее из таблицы User
            c.removeCurrentFilmDataFromUsers();

            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No CurrentFilmData with id: " + id + " in database ");
                transaction.rollback();
            }
            transaction.commit();
        }
    }

    public List<CurrentFilmData> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from CurrentFilmData", CurrentFilmData.class).getResultList();
        }

    }

    public Set<User> getUsers(long id) {
        try (final Session session = factory.openSession()) {
            CurrentFilmData c = session.get(CurrentFilmData.class, id);
            Hibernate.initialize(c.getUsers());
            return c.getUsers();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }


}
