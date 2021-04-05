package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.CafeBarDAO;
import com.kozyrevych.app.model.CafeBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CafeBarService {
    @Autowired
    CafeBarDAO cafeBarDAO;

    public void save(CafeBar cafeBar) {
       cafeBarDAO.save(cafeBar);
    }

    public void delete(CafeBar cafeBar) {
        cafeBarDAO.delete(cafeBar);
    }

    public void deleteById(long id) {
        CafeBar c = get(id);
        delete(c);
    }

    
    public void update(CafeBar cafeBar) {
        cafeBarDAO.update(cafeBar);
    }

    
    public CafeBar get(long id) {
        return cafeBarDAO.get(id);
    }

    
    public List<CafeBar> getAll() {
        return cafeBarDAO.getAll();
    }
}
