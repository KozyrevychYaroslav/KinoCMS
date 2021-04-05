package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Image;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class ImageDAO implements DAO<Image> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Image image) {
        entityManager.persist(image);
    }

    @Override
    public void delete(Image image) {
        try {
            entityManager.remove(image);
        } catch (IllegalArgumentException e) {
            System.out.println("No image: " + image + " in database ");
        }
    }

    @Override
    public void update(Image image) {
        try {
            entityManager.merge(image);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update image");
        }
    }

    @Override
    public Image get(long id) {
        try {
            return entityManager.find(Image.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Image getByName(String name) {
        Query query = entityManager.createQuery("from Image c where imageName =: name");
        query.setParameter("name", name);
        try {
            return (Image) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Image> getAll() {
        return entityManager.createQuery("from Image", Image.class).getResultList();
    }
}
