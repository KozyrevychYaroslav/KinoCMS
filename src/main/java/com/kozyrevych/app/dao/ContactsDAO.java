package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Contacts;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ContactsDAO implements DAO<Contacts> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Contacts contacts) {
        entityManager.persist(contacts);
    }

    @Override
    public void delete(Contacts contacts) {
        try {
            entityManager.remove(contacts);
        } catch (IllegalArgumentException e) {
            System.out.println("No contacts: " + contacts + " in database ");
        }

    }

    @Override
    public void update(Contacts contacts) {
        try {
            entityManager.merge(contacts);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update contacts");
        }
    }

    @Override
    public Contacts get(long id) {
        return entityManager.find(Contacts.class, id);

    }

    @Override
    public List<Contacts> getAll() {
        return entityManager.createQuery("from Contacts ", Contacts.class).getResultList();

    }
}
