package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Image;
import com.kozyrevych.app.model.Stock;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

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

    public Cinema get(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select c from Cinema c where cinemaName =: name");
            query.setParameter("name", name);
            try {
                return (Cinema) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public Set<Stock> getStocks(String name) {
        try (final Session session = factory.openSession()) {
            Cinema c = get(name);
            c = session.get(Cinema.class, c.getId());
            Hibernate.initialize(c.getStocks());
            return c.getStocks();
        }
    }

    public Set<Image> getImages(String name) {
        try (final Session session = factory.openSession()) {
            Cinema c = get(name);
            c = session.get(Cinema.class, c.getId());
            Hibernate.initialize(c.getImages());
            return c.getImages();
        }
    }

    public CafeBar getCafeBar(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Cinema.class, id).getCafeBar();
        }
    }

    public void update(Cinema cinema) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(cinema);
            } catch(IllegalArgumentException e) {
                System.out.println("Can't update cinema");
            }
            transaction.commit();
        }
    }

    public void delete(String name) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Cinema c = get(name);
            try {
                session.delete(c);
            } catch(IllegalArgumentException e) {
                System.out.println("No cinema name: " + name + " in database ");
            }
            transaction.commit();
        }
    }

    public List<Cinema> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Cinema", Cinema.class).getResultList();
        }

    }


}
