package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.FilmDataDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FilmData;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class FilmDataService {
    @Autowired
    FilmDataDAO filmDataDAO;


    public void save(FilmData filmData) {
        filmDataDAO.save(filmData);
    }

    public FilmData get(long id) {
        return filmDataDAO.get(id);
    }

    public FilmData getByTitle(String title) {
        return filmDataDAO.getByTitle(title);
    }

    public void update(FilmData filmData) {
        filmDataDAO.update(filmData);
    }

    public void delete(FilmData filmData) {
        filmDataDAO.delete(filmData);
    }

    public void deleteByTitle(String title) {
        FilmData c = getByTitle(title);

        // так как FilmData является главной таблицей в связи manyToMany, а мы решили удалить элемент из нее,
        // то сначала надо очистить все ссылки на нее из таблицы Cinema

        filmDataDAO.delete(c);
    }


    public List<FilmData> getAll() {
        return filmDataDAO.getAll();

    }

    public Set<Cinema> getCinemas(long id) {
        FilmData filmData = get(id);
        Hibernate.initialize(filmData.getCinemas());
        return filmData.getCinemas();
    }

    public Set<CurrentFilmData> getCurrentFilmDatas(long id) {
        try{
            FilmData c = get(id);
            Hibernate.initialize(c.getCurrentFilmData());
            return c.getCurrentFilmData();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

}
