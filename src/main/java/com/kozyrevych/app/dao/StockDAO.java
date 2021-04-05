package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Stock;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class StockDAO implements DAO<Stock> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Stock stock) {
        entityManager.persist(stock);

    }

    @Override
    public void delete(Stock stock) {
        try {
            entityManager.remove(stock);
        } catch (IllegalArgumentException e) {
            System.out.println("No stock: " + stock + " in database ");
        }
    }

    @Override
    public void update(Stock stock) {
        try {
            entityManager.merge(stock);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update stock");
        }
    }

    @Override
    public Stock get(long id) {
        try {
            return entityManager.find(Stock.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Stock getByName(String name) {
        try {
            return (Stock) entityManager.
                    createQuery("select c from Stock c where title =: name").
                    setParameter("name", name).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Stock> getAll() {
        return entityManager.createQuery("from Stock", Stock.class).getResultList();
    }
}
