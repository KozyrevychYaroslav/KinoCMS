package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CinemaDAO {
    private SessionFactory factory;
    private CurrentFilmDataDAO currentFilmDataDAO;

    @Autowired
    public CinemaDAO(SessionFactory factory, CurrentFilmDataDAO currentFilmDataDAO) {
        this.factory = factory;
        this.currentFilmDataDAO = currentFilmDataDAO;
    }

    public void save(Cinema cinema) {
        try (final Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(cinema);
            transaction.commit();
        }
    }

    public Cinema get(String title) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select c from Cinema c where cinemaName =: title");
            query.setParameter("title", title);
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

    public List<Integer> getNumberOfFilmData(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select count(*) from Cinema c " +
                    "join c.filmHalls fh " +
                    "join  fh.currentFilmsData cfd " +
                    "join cfd.filmData fd " +
                    "where c.cinemaName =: name " +
                    "group by fd.id ");
            query.setParameter("name", name);
            return query.getResultList();
        } catch (NullPointerException | NoResultException e) {
            System.out.println("no cinema: " + name);
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

    public CafeBar getCafeBar(String name) {
        try (final Session session = factory.openSession()) {
            return get(name).getCafeBar();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Advertising getAdvertising(String name) {
        try (final Session session = factory.openSession()) {
            return get(name).getAdvertising();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Contacts getContacts(String name) {
        try (final Session session = factory.openSession()) {
            return get(name).getContacts();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public MobileApp getMobileApp(String name) {
        try (final Session session = factory.openSession()) {
            return get(name).getMobileApp();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public ChildrensRoom getChildrensRoom(String name) {
        try (final Session session = factory.openSession()) {
            return get(name).getChildrensRoom();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
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
        } catch (NoResultException e) {
            return null;
        }

    }

    public Set<FilmData> getFilmsDataInCinema(String name) {
        try (final Session session = factory.openSession()) {
            Cinema c = get(name);
            try {
                c = session.get(Cinema.class, c.getId());
                Hibernate.initialize(c.getFilmsData());
                return c.getFilmsData();
            } catch (IllegalArgumentException e) {
                System.out.println("No cinema name: " + name + " in database ");
                return null;
            }
        }
    }

    public Map<Cinema, Set<FilmData>> getFilmsDataInAllCinemas() {
        try (final Session session = factory.openSession()) {
            Map<Cinema, Set<FilmData>> map = new LinkedHashMap<>();
            getAll().forEach(i -> map.put(i, getFilmsDataInCinema(i.getCinemaName())));
            return map;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public int[] getNumberOfFilmsInCinemaEveryMonth(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select CFD from CurrentFilmData CFD join CFD.filmHall FH" +
                    " join FH.cinema C where C.cinemaName =: name");
            query.setParameter("name", name);

            List<CurrentFilmData> currentFilmData = query.getResultList();
            int[] numbers = new int[12];

            for (int i = 1; i <= 12; i++) {
                for (CurrentFilmData currentFilmData1 : currentFilmData) {
                    LocalDateTime localDateTime = LocalDateTime.parse(
                            currentFilmData1.getFilmTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    );
                    if (localDateTime.getMonthValue() == i) numbers[i - 1] += 1;
                }
            }
            return numbers;
        } catch (IllegalArgumentException | NoResultException e) {
            return new int[12];
        }
    }

    public List<String> getAllFilmsInCinema(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select fd from Cinema c " +
                    "join c.filmHalls fh " +
                    "join  fh.currentFilmsData cfd " +
                    "join cfd.filmData fd " +
                    "where c.cinemaName =: name " +
                    "group by fd.id");
            query.setParameter("name", name);
            return (List<String>) query.getResultList().stream().
                    map(o -> ((FilmData) o).
                            getFilmTitle()).
                    collect(Collectors.toList());
        } catch (NullPointerException | NoResultException e) {
            System.out.println("no cinema: " + name);
            return null;
        }

    }

    public int[] getNumberOfUsersEveryMonth(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select cfd from Cinema c " +
                    "join c.filmHalls fh " +
                    "join  fh.currentFilmsData cfd " +
                    "where c.cinemaName =: name");
            query.setParameter("name", name);

            List<CurrentFilmData> currentFilmData = query.getResultList();
            int[] numbers = new int[12];

            for (int i = 1; i <= 12; i++) {
                for (CurrentFilmData currentFilmData1 : currentFilmData) {
                    LocalDateTime localDateTime = LocalDateTime.parse(
                            currentFilmData1.getFilmTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    );
                    if (localDateTime.getMonthValue() == i) {
                        try {
                            Query query1 = session.createQuery("select count(*) from CurrentFilmData cfd " +
                                    "join cfd.users u " +
                                    "where cfd.id =: id group by cfd.id");
                            query1.setParameter("id", currentFilmData1.getId());
                            numbers[i - 1] += (Long) query1.getSingleResult();
                        } catch (NoResultException e) {
                            numbers[i - 1] += 0;
                        }
                    }
                }
            }
            return numbers;
        } catch (NullPointerException | NoResultException e) {
            System.out.println("no cinema: " + name);
            return null;
        }
    }

    public int[] getProfit(String name) {
        try (final Session session = factory.openSession()) {
            Query query = session.createQuery("select CFD from CurrentFilmData CFD join CFD.filmHall FH" +
                    " join FH.cinema C where C.cinemaName =: name");
            query.setParameter("name", name);

            List<CurrentFilmData> currentFilmData = query.getResultList();
            int[] numbers = new int[12];

            for (int i = 1; i <= 12; i++) {
                for (CurrentFilmData currentFilmData1 : currentFilmData) {
                    LocalDateTime localDateTime = LocalDateTime.parse(
                            currentFilmData1.getFilmTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    );
                    if (localDateTime.getMonthValue() == i) numbers[i - 1] +=
                            currentFilmDataDAO.getUsers(currentFilmData1.getId()).stream().
                                    map(o -> currentFilmData1.getPrice()).
                                    reduce(0.0, Double::sum);
                }
                if (i == 12) continue;
                numbers[i] = numbers[i - 1];
            }
            return numbers;
        } catch (IllegalArgumentException | NoResultException e) {
            return new int[12];
        }
    }

}
