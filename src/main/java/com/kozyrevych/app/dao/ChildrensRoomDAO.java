package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.ChildrensRoom;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ChildrensRoomDAO implements DAO<ChildrensRoom> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(ChildrensRoom ChildrensRoom) {
        entityManager.persist(ChildrensRoom);

    }

    @Override
    public void delete(ChildrensRoom childrensRoom) {
        try {
            entityManager.remove(childrensRoom);
        } catch (IllegalArgumentException e) {
            System.out.println("No childrens room: " + childrensRoom + " in database ");
        }

    }

    @Override
    public void update(ChildrensRoom childrensRoom) {
        try {
            entityManager.merge(childrensRoom);
        } catch (IllegalArgumentException e) {
            System.out.println("Can't update cafe bar");
        }
    }

    @Override
    public ChildrensRoom get(long id) {
        return entityManager.find(ChildrensRoom.class, id);

    }

    @Override
    public List<ChildrensRoom> getAll() {
        return entityManager.createQuery("from ChildrensRoom ", ChildrensRoom.class).getResultList();

    }
}
