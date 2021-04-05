package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.UserDAO;
import com.kozyrevych.app.model.CurrentFilmData;
import com.kozyrevych.app.model.FreePlace;
import com.kozyrevych.app.model.Gender;
import com.kozyrevych.app.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class UserService {
    @Autowired
    UserDAO userDAO;


    public void save(User user) {
        userDAO.save(user);
    }


    public User get(long id) {
        return userDAO.get(id);
    }

    public User getByPhoneNumber(String phoneNumber) {
        return userDAO.getByPhoneNumber(phoneNumber);
    }


    public void update(User user) {
        userDAO.update(user);
    }


    public void delete(User user) {
        userDAO.delete(user);

    }

    public void deleteByPhoneNumber(String phoneNumber) {
        User c = getByPhoneNumber(phoneNumber);
        delete(c);
    }


    public List<User> getAll() {
        return userDAO.getAll();
    }

    public Set<CurrentFilmData> getCurrentFilmDatas(String phoneNumber) {
        try {
            User c = getByPhoneNumber(phoneNumber);
            Hibernate.initialize(c.getCurrentFilmsData());
            return c.getCurrentFilmsData();
        } catch (NullPointerException e) {
            System.out.println("No phoneNumber: " + phoneNumber);
            return null;
        }
    }

    public Set<FreePlace> getFreePlaces(String phoneNumber) {
        try {
            User c = getByPhoneNumber(phoneNumber);
            Hibernate.initialize(c.getFreePlaces());
            return c.getFreePlaces();
        } catch (NullPointerException e) {
            System.out.println("No phoneNumber: " + phoneNumber);
            return null;
        }
    }

    public Map<Gender, Long> getNumberOfGenders() {
        List<User> users = getAll();
        Map<Gender, Long> genders = new LinkedHashMap<>();
        genders.put(Gender.FEMALE, users.stream().filter(i -> i.getGender() == Gender.FEMALE).count());
        genders.put(Gender.MALE, users.stream().filter(i -> i.getGender() == Gender.MALE).count());
        return genders;
    }
}
