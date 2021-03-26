package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.service.NullServiceException;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

public class CinemaDAO {
    private SessionFactory factory;

    public CinemaDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void save(Cinema cinema) {
        //если попытаться добавить обьект, первичный ключ которого уже существует, он все равно именно добавится, а не
        //заапдэйтится, а в значении первичного ключа станет последнее значение + 1 (как обычно при добавлении нового обьекта)
        //. Есть удобный метод saveOrUpdate (который создаст если нету, и заапдеэйтит если есть)
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(cinema);
            transaction.commit();
        }
    }

    public Cinema get(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select c from Cinema c where cinemaName =: name");
            query.setParameter("name", name);
            try {
                return (Cinema) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public Set<Stock> getStocks(String name) {
        try (final Session session = factory.openSession()) {
            Cinema c = get(name);
            c = session.get(Cinema.class, c.getId());
            Hibernate.initialize(c.getStocks());
            return c.getStocks();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Set<News> getNews(String name) {
        try (final Session session = factory.openSession()) {
            Cinema c = get(name);
            c = session.get(Cinema.class, c.getId());
            Hibernate.initialize(c.getNews());
            return c.getNews();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Set<FilmHall> getFilmHalls(String name) {
        try (final Session session = factory.openSession()) {
            Cinema c = get(name);
            c = session.get(Cinema.class, c.getId());
            Hibernate.initialize(c.getFilmHalls());
            return c.getFilmHalls();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Set<FilmData> getFilmsData(long id) {
        try (final Session session = factory.openSession()) {
            Cinema c = session.get(Cinema.class, id);
            Hibernate.initialize(c.getFilmsData());
            return c.getFilmsData();
        } catch (NullPointerException e) {
            System.out.println("no id: " + id);
            return null;
        }

    }

    public Set<Image> getImages(String name) {
        try (final Session session = factory.openSession()) {
            Cinema c = get(name);
            c = session.get(Cinema.class, c.getId());
            Hibernate.initialize(c.getImages());
            return c.getImages();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public CafeBar getCafeBar(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Cinema.class, id).getCafeBar();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public Advertising getAdvertising(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Cinema.class, id).getAdvertising();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public Contacts getContacts(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Cinema.class, id).getContacts();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public MobileApp getMobileApp(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Cinema.class, id).getMobileApp();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public ChildrensRoom getChildrensRoom(long id) {
        try (final Session session = factory.openSession()) {
            return session.get(Cinema.class, id).getChildrensRoom();
        } catch (NullPointerException e) {
            System.out.println("No id: " + id);
            return null;
        }
    }

    public void update(Cinema cinema) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(cinema);
            } catch (IllegalArgumentException e) {
                System.out.println("Can't update cinema");
            }
            transaction.commit();
        }
    }

    public void delete(String name) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Cinema c = get(name);
            try {
                session.delete(c);
            } catch (IllegalArgumentException e) {
                System.out.println("No cinema name: " + name + " in database ");
            }
            transaction.commit();
        }
    }

    public List<Cinema> getAll() {
        try (final Session session = factory.openSession()) {
            return session.createQuery("from Cinema", Cinema.class).getResultList();
        }

    }


}
