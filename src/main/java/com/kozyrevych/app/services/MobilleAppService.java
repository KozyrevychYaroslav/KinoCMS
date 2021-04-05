package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.MobileAppDAO;
import com.kozyrevych.app.model.MobileApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MobilleAppService {
    @Autowired
    MobileAppDAO mobileAppDAO;


    public void save(MobileApp MobileApp) {
        mobileAppDAO.save(MobileApp);
    }


    public void delete(MobileApp mobileApp) {
        mobileAppDAO.delete(mobileApp);
    }

    public void deleteById(long id) {
        MobileApp c = get(id);
        delete(c);
    }


    public void update(MobileApp mobileApp) {
       mobileAppDAO.update(mobileApp);
    }


    public MobileApp get(long id) {
        return mobileAppDAO.get(id);
    }


    public List<MobileApp> getAll() {
        return mobileAppDAO.getAll();
    }
}
