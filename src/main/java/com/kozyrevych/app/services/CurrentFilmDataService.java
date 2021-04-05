package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.CurrentFilmDataDAO;
import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CurrentFilmDataService {
    @Autowired
    CurrentFilmDataDAO currentFilmDataDAO;
    
    
    public void save(CurrentFilmData currentFilmData) {
        currentFilmDataDAO.save(currentFilmData);
    }

    
    public CurrentFilmData get(long id) {
        return currentFilmDataDAO.get(id);
    }

    
    public void update(CurrentFilmData currentFilmData) {
       currentFilmDataDAO.update(currentFilmData);
    }

    
    public void delete(CurrentFilmData currentFilmData) {
        currentFilmDataDAO.delete(currentFilmData);
    }

    public void deleteById(long id) {
        CurrentFilmData c = get(id);
        // так как currentFilmData является главной таблицей в связи manyToMany, а мы решили удалить элемент из нее,
        // то сначала надо очистить все ссылки на нее из таблицы User
        c.removeCurrentFilmDataFromUsers();
        delete(c);
    }

    
    public List<CurrentFilmData> getAll() {
        return currentFilmDataDAO.getAll();
    }

    public Set<User> getUsers(long id) {
        try {
            CurrentFilmData c = get(id);
            Hibernate.initialize(c.getUsers());
            return c.getUsers();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public long getNumberOfCurrentFilmData(long id) {
        return currentFilmDataDAO.getNumberOfCurrentFilmData(id);
    }
}
