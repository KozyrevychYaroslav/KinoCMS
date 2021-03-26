package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class ImageDAO {
    private SessionFactory factory;

    public ImageDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(Image image) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(image);
            transaction.commit();
        }
    }

    public void delete(String name) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Image c = get(name);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No image name: " + name + " in database ");
            }
            transaction.commit();
        }
    }

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

    public List<Image> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Image", Image.class).getResultList();
        }
    }
}
