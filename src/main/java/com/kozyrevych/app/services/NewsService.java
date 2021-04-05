package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.NewsDAO;
import com.kozyrevych.app.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service
@Transactional
public class NewsService {
    @Autowired
    NewsDAO newsDAO;

    
    public void save(News news) {
        newsDAO.save(news);
    }

    
    public void delete(News news) {
        newsDAO.delete(news);
    }

    public void deleteByName(String name) {
        News c = getByName(name);
        delete(c);
    }

    
    public void update(News news) {
        newsDAO.update(news);
    }

    
    public News get(long id) {
       return newsDAO.get(id);
    }

    public News getByName(String name) {
       return newsDAO.getByName(name);
    }

    
    public List<News> getAll() {
        return newsDAO.getAll();
    }
}
