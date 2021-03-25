package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Cinema;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CinemaDAO {
    private SessionFactory factory;

    public CinemaDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(Cinema cinema) {
        //если попытаться добавить обьект, первичный ключ которого уже существует, он все равно именно добавится, а не
        //заапдэйтится, а в значении первичного ключа станет последнее значение + 1 (как обычно при добавлении нового обьекта)
        //. Есть удобный метод saveOrUpdate (который создаст если нету, и заапдеэйтит если есть)
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(cinema);
            transaction.commit();
        }
    }

    public Cinema get(long key) {
        try (final Session session = factory.openSession()) {
            return session.get(Cinema.class, key);
        }
    }

    public void update(Cinema cinema) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(cinema);
            transaction.commit();
        }
    }

    public void delete(Cinema cinema) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(cinema);
            transaction.commit();
        }
    }

    public List<Cinema> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Cinema", Cinema.class).getResultList();
        }

    }


}
