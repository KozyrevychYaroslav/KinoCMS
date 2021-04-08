package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.SEO;
import com.kozyrevych.app.model.SEO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class SeoDAO implements DAO<SEO> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(SEO SEO) {
        entityManager.persist(SEO);
    }

    @Override
    public void delete(SEO SEO) {
        try {
            entityManager.remove(SEO);
        } catch (IllegalArgumentException e) {
            System.out.println("No seo: " + SEO + " in database ");
        }
    }

    @Override
    public void update(SEO SEO) {
        try {
            entityManager.merge(SEO);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update seo");
        }
    }

    @Override
    public SEO get(long id) {
        return entityManager.find(SEO.class, id);
    }

    @Override
    public List<SEO> getAll() {
        return entityManager.createQuery("from SEO ", SEO.class).getResultList();
    }
}
