package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.AdvertisingDAO;
import com.kozyrevych.app.model.Advertising;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdvertisingService {
    @Autowired
    private AdvertisingDAO advertisingDAO;

    public void save(Advertising advertising) {
        advertisingDAO.save(advertising);
    }

    public void delete(Advertising advertising) {
       advertisingDAO.delete(advertising);
    }

    public void deleteById(long id) {
        Advertising c = get(id);
        advertisingDAO.delete(c);
    }

    public void update(Advertising advertising) {
        advertisingDAO.update(advertising);
    }

    public Advertising get(long id) {
      return advertisingDAO.get(id);
    }

    public List<Advertising> getAll() {
        return advertisingDAO.getAll();
    }
}
