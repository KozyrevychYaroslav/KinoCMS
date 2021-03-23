package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Cinema;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CinemaDAO {
    private final SessionFactory factory;

    public CinemaDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void add(Cinema cinema) {
        //если попытаться добавить обьект, первичный ключ которого уже существует, он все равно именно добавится, а не
        //заапдэйтится, а в значении первичного ключа станет последнее значение + 1 (как обычно при добавлении нового обьекта)
        //. Есть удобный метод saveOrUpdate (который создаст если нету, и заапдеэйтит если есть)
        try(final Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(cinema);
            session.getTransaction().commit();
        }
    }

    public Cinema get(long key) {
        try(final Session session = factory.openSession()) {
            Cinema cinema = session.get(Cinema.class, key);
            return cinema == null ? new Cinema() : cinema;
        }
    }

    public void update(Cinema cinema) {
        try(Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(cinema);
            session.getTransaction().commit();
        }
    }

    public void delete(Cinema cinema) {
        try(Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(cinema);
            session.getTransaction().commit();
        }
    }

    public void saveOrUpdate(Cinema cinema) {
        try(Session session = factory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(cinema);
            session.getTransaction().commit();
        }
    }
}
