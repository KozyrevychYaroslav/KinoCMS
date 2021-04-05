package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.FreePlaceDAO;
import com.kozyrevych.app.model.FreePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class FreePlaceService {
    @Autowired
    FreePlaceDAO freePlaceDAO;

    public void save(FreePlace freePlace) {
        freePlaceDAO.save(freePlace);
    }

    public FreePlace get(long id) {
        return freePlaceDAO.get(id);
    }

    public void update(FreePlace freePlace) {
        freePlaceDAO.update(freePlace);
    }

    public void delete(FreePlace freePlace) {
        freePlaceDAO.delete(freePlace);
    }

    public void deleteById(long id) {
        FreePlace c = get(id);
        delete(c);
    }

    public List<FreePlace> getAll() {
        return freePlaceDAO.getAll();
    }
}
