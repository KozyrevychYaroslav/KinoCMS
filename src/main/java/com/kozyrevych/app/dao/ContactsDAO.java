package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.Contacts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactsDAO implements DAO<Contacts, Long>{
    private SessionFactory factory;

    @Autowired
    public ContactsDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Contacts contacts) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(contacts);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Contacts c = get(id);
            c = (Contacts) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No contacts with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
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

    @Override
    public Contacts get(Long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Contacts.class, id);
        }
    }

    @Override
    public List<Contacts> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Contacts ", Contacts.class).getResultList();
        }
    }
}
