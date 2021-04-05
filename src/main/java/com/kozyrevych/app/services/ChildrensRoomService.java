package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.ChildrensRoomDAO;
import com.kozyrevych.app.model.ChildrensRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChildrensRoomService {
    @Autowired
    ChildrensRoomDAO childrensRoomDAO;

    public void save(ChildrensRoom childrensRoom) {
        childrensRoomDAO.save(childrensRoom);

    }

    public void delete(ChildrensRoom childrensRoom) {
        childrensRoomDAO.delete(childrensRoom);
    }

    public void deleteById(long id) {
        ChildrensRoom c = get(id);
        delete(c);
    }

    public void update(ChildrensRoom childrensRoom) {
        childrensRoomDAO.update(childrensRoom);
    }

    public ChildrensRoom get(long id) {
        return childrensRoomDAO.get(id);

    }

    public List<ChildrensRoom> getAll() {
        return childrensRoomDAO.getAll();
    }
}
