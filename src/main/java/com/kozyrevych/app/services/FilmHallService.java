package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.FilmHallDAO;
import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FilmHall;
import com.kozyrevych.app.model.FreePlace;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class FilmHallService {
    @Autowired
    FilmHallDAO filmHallDAO;
    
    public void save(FilmHall filmHall) {
        filmHallDAO.save(filmHall);
    }
    
    public void delete(FilmHall filmHall) {
        filmHallDAO.delete(filmHall);
    }

    public void deleteByFilmHallNumber(int filmHallNumber) {
        FilmHall c = getByFilmHallNumber(filmHallNumber);
        delete(c);
    }

    
    public void update(FilmHall filmHall) {
        filmHallDAO.update(filmHall);
    }

    
    public FilmHall get(long id) {
        return filmHallDAO.get(id);
    }

    public FilmHall getByFilmHallNumber(int filmHallNumber) {
       return filmHallDAO.getByFilmHallNumber(filmHallNumber);
    }

    
    public List<FilmHall> getAll() {
        return filmHallDAO.getAll();
    }

    public Set<CurrentFilmData> getCurrentFilmDatas(long id) {
        try {
            FilmHall c = get(id);
            Hibernate.initialize(c.getCurrentFilmsData());
            return c.getCurrentFilmsData();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public Set<FreePlace> getFreePlaces(long id) {
        try {
            FilmHall c = get(id);
            Hibernate.initialize(c.getFreePlaces());
            return c.getFreePlaces();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

}
