package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.News;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class NewsDAO implements DAO<News> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(News news) {
        entityManager.persist(news);
    }

    @Override
    public void delete(News news) {
        try {
            entityManager.remove(news);
        } catch (IllegalArgumentException e) {
            System.out.println("No News: " + news + " in database ");
        }
    }

    @Override
    public void update(News news) {
        try {
            entityManager.merge(news);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update News");
        }
    }

    @Override
    public News get(long id) {
        try {
            return entityManager.find(News.class, id);
        } catch (NoResultException e) {
            return null;
        }

    }

    public News getByName(String name) {
        Query query = entityManager.createQuery("select c from News c where newsTitle =: name");
        query.setParameter("name", name);
        try {
            return (News)  entityManager.
                            createQuery("select c from News c where newsTitle =: name").
                            setParameter("name", name).
                            getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<News> getAll() {
        return entityManager.createQuery("from News", News.class).getResultList();
    }
}
