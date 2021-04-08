package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.SeoDAO;
import com.kozyrevych.app.model.SEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeoService {
    @Autowired
    SeoDAO SEODAO;

    public void save(SEO SEO) {
        SEODAO.save(SEO);
    }

    public void delete(SEO SEO) {
        SEODAO.delete(SEO);
    }

    public void deleteById(long id) {
        SEO c = get(id);
        delete(c);
    }


    public void update(SEO SEO) {
        SEODAO.update(SEO);
    }


    public SEO get(long id) {
        return SEODAO.get(id);
    }


    public List<SEO> getAll() {
        return SEODAO.getAll();
    }
}
