package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.ContactsDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Contacts;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactsTest {
    @Autowired
    private  ContactsDAO contactsDAO = null;
    @Autowired
    private CinemaDAO cinemaDAO = null;



    @Test
    @DisplayName("Add and get data to Contacts table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Contacts contacts = new Contacts();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        contacts.setCinema(cinema);
        contacts.setInfo("some info 1");
        contacts.setCinema(cinema);
        cinema.setContacts(contacts);
        cinemaDAO.save(cinema);

        assertEquals(contacts, contactsDAO.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(contactsDAO.get(1L), cinemaDAO.getContacts("Высоцкого"));
    }

    @Test
    @DisplayName("Get all rows from contacts table")
    @Order(4)
    public void m4() {
        Cinema cinema = new Cinema();
        Contacts contacts = new Contacts();

        cinema.setAddress("Бочароваа 20");
        cinema.setCinemaName("Бочарова");
        cinema.setInfo("some info");
        contacts.setInfo("some info 2");
        contacts.setCinema(cinema);
        cinema.setContacts(contacts);
        cinemaDAO.save(cinema);

        assertEquals(2, contactsDAO.getAll().size());
    }


    @Test
    @DisplayName("Delete data from contacts table using Cinema and contacts")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaDAO.getAll().size());

        cinemaDAO.delete("Высоцкого");

        assertEquals(1, cinemaDAO.getAll().size());

        assertEquals(1, contactsDAO.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaDAO.get("Высоцкого"));
    }

    @Test
    @DisplayName("update data in contacts table")
    @Order(6)
    public void m6() {
        Contacts contacts = contactsDAO.get(2L);

        contacts.setInfo("UPDATED info");
        contactsDAO.update(contacts);

        assertEquals(contacts, contactsDAO.get(2L));

        contactsDAO.delete(2L);

        assertNull(contactsDAO.get(1L));

        assertNull(contactsDAO.get(3L));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }
}