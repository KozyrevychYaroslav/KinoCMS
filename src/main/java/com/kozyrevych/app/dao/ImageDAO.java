package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;

@Component
public class ImageDAO implements DAO<Image, String>{
    private SessionFactory factory;

    @Autowired
    public ImageDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Image image) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(image);
            transaction.commit();
        }
    }

    @Override
    public void delete(String name) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Image c = get(name);
            c = (Image) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No image name: " + name + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
    public void update(Image image) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(image);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update image");
            }
            transaction.commit();
        }
    }

    @Override
    public Image get(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("from Image c where imageName =: name");
            query.setParameter("name", name);
            try {
                return (Image) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    @Override
    public List<Image> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Image", Image.class).getResultList();
        }
    }
}
