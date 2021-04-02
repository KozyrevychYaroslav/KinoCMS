package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.ChildrensRoom;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChildrensRoomDAO {
    private SessionFactory factory;

    @Autowired
    public ChildrensRoomDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(ChildrensRoom ChildrensRoom) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(ChildrensRoom);
            transaction.commit();
        }
    }

    public void delete(long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            ChildrensRoom c = get(id);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No cafe bar with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    public void update(ChildrensRoom childrensRoom) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(childrensRoom);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update cafe bar");
            }
            transaction.commit();
        }
    }

    public ChildrensRoom get(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(ChildrensRoom.class, id);
        }
    }

    public List<ChildrensRoom> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from ChildrensRoom ", ChildrensRoom.class).getResultList();
        }
    }
}
