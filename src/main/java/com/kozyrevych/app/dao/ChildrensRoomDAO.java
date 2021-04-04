package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import com.kozyrevych.app.model.CafeBar;
import com.kozyrevych.app.model.ChildrensRoom;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChildrensRoomDAO implements DAO<ChildrensRoom, Long>{
    private SessionFactory factory;

    @Autowired
    public ChildrensRoomDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(ChildrensRoom ChildrensRoom) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(ChildrensRoom);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            ChildrensRoom c = get(id);
            c = (ChildrensRoom) session.merge(c);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No cafe bar with id: " + id + " in database ");
            }
            transaction.commit();
        }
    }

    @Override
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

    @Override
    public ChildrensRoom get(Long id) {
        try (final Session session = factory.openSession()) {
            return session.get(ChildrensRoom.class, id);
        }
    }

    @Override
    public List<ChildrensRoom> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from ChildrensRoom ", ChildrensRoom.class).getResultList();
        }
    }
}
