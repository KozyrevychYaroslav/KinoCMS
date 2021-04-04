package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.FreePlace;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FreePlaceDAO implements DAO<FreePlace, Long>{
    private SessionFactory factory;

    @Autowired
    public FreePlaceDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(FreePlace FreePlace) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(FreePlace);
            transaction.commit();
        }
    }

    @Override
    public FreePlace get(Long id) {
        try (final Session session = factory.openSession()) {
            return session.get(FreePlace.class, id);
        }
    }

    @Override
    public void update(FreePlace FreePlace) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(FreePlace);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update FreePlace");
            }
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            FreePlace c = get(id);
            c = (FreePlace) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No FreePlace with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
    public List<FreePlace> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from FreePlace", FreePlace.class).getResultList();
        }

    }
}
