package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Contacts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactsDAO {
    private SessionFactory factory;

    @Autowired
    public ContactsDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(Contacts contacts) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(contacts);
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Contacts c = get(id);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No contacts with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    public void update(Contacts contacts) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(contacts);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update contacts");
            }
            transaction.commit();
        }
    }

    public Contacts get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Contacts.class, id);
        }
    }

    public List<Contacts> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Contacts ", Contacts.class).getResultList();
        }
    }
}
