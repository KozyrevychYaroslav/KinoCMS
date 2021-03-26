package com.kozyrevych.app;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.ContactsDAO;
import com.kozyrevych.app.model.Cinema;
import com.kozyrevych.app.model.Contacts;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ContactsTest {
    private static SessionFactory sessionFactory = null;
    private static ContactsDAO contactsDAO = null;
    private static CinemaDAO cinemaDAO = null;

    @BeforeAll
    @DisplayName("Start session factory")
    public static void configStart() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        cinemaDAO = new CinemaDAO(sessionFactory);
        contactsDAO = new ContactsDAO(sessionFactory);
    }

    @AfterAll
    @DisplayName("close session factory")
    public static void configEnd() {
        SessionFactoryUtil.closeSessionFactory();
    }

    @Test
    @DisplayName("Add and get data to Contacts table")
    @Order(2)
    public void m1() {
        Cinema cinema = new Cinema();
        Contacts contacts = new Contacts();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Высоцкого");
        cinema.setInfo("some info");
        cinemaDAO.save(cinema);
        contacts.setCinema(cinema);
        contacts.setInfo("some info 1");
        contactsDAO.save(contacts);

        assertEquals(contacts, contactsDAO.get(1));
    }

    @Test
    @DisplayName("Checked one to one relationship")
    @Order(3)
    public void m2() {
        assertEquals(contactsDAO.get(1), cinemaDAO.getContacts(1));
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
        cinemaDAO.save(cinema);
        contacts.setCinema(cinema);
        contacts.setInfo("some info 2");
        contactsDAO.save(contacts);

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
        Contacts contacts = contactsDAO.get(2);

        contacts.setInfo("UPDATED info");
        contactsDAO.update(contacts);

        assertEquals(contacts, contactsDAO.get(2));

        contactsDAO.delete(2);

        assertNull(contactsDAO.get(1));

        assertNull(contactsDAO.get(3));

        assertNotNull(cinemaDAO.get("Бочарова"));
    }
}