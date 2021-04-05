package com.kozyrevych.app;

import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Contacts;
import com.kozyrevych.app.services.CinemaService;
import com.kozyrevych.app.services.ContactsService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactsTest {
    @Autowired
    private ContactsService contactsService = null;
    @Autowired
    private CinemaService cinemaService = null;



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
        cinemaService.save(cinema);

        assertEquals(contacts, contactsService.get(1L));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(contactsService.get(1L), cinemaService.getContacts("Высоцкого"));
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
        cinemaService.save(cinema);

        assertEquals(2, contactsService.getAll().size());
    }


    @Test
    @DisplayName("Delete data from contacts table using Cinema and contacts")
    @Order(5)
    public void m5() {
        assertEquals(2, cinemaService.getAll().size());

        cinemaService.deleteByName("Высоцкого");

        assertEquals(1, cinemaService.getAll().size());

        assertEquals(1, contactsService.getAll().size(), "каскадное удаление не работает");

        assertNull(cinemaService.getByName("Высоцкого"));
    }

    @Test
    @DisplayName("update data in contacts table")
    @Order(6)
    public void m6() {
        Contacts contacts = contactsService.get(2L);

        contacts.setInfo("UPDATED info");
        contactsService.update(contacts);

        assertEquals(contacts, contactsService.get(2L));

        contactsService.deleteById(2L);

        assertNull(contactsService.get(1L));

        assertNull(contactsService.get(3L));

        assertNotNull(cinemaService.getByName("Бочарова"));
    }
}