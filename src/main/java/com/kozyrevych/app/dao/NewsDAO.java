package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.List;

public class NewsDAO {
    private SessionFactory factory;

    @Autowired
    public NewsDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(News news) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(news);
            transaction.commit();
        }
    }

    public void delete(String name) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            News c = get(name);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No News name: " + name + " in database ");
            }
            transaction.commit();
        }
    }

    public void update(News news) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(news);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update News");
            }
            transaction.commit();
        }
    }

    public News get(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select c from News c where newsTitle =: name");
            query.setParameter("name", name);
            try {
                return (News) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public List<News> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from News", News.class).getResultList();
        }
    }
}
