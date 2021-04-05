package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.ContactsDAO;
import com.kozyrevych.app.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ContactsService {
    @Autowired
    private ContactsDAO contactsDAO;


    public void save(Contacts contacts) {
        contactsDAO.save(contacts);
    }


    public void delete(Contacts contacts) {
        contactsDAO.delete(contacts);
    }

    public void deleteById(long id) {
        Contacts c = get(id);
        contactsDAO.delete(c);
    }


    public void update(Contacts contacts) {
        contactsDAO.update(contacts);
    }


    public Contacts get(long id) {
        return contactsDAO.get(id);
    }


    public List<Contacts> getAll() {
        return contactsDAO.getAll();
    }
}
