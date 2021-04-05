package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.CinemaDAO;
import com.kozyrevych.app.dao.CurrentFilmDataDAO;
import com.kozyrevych.app.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CinemaService {
    @Autowired
    private CinemaDAO cinemaDAO;
    @Autowired
    private CurrentFilmDataService currentFilmDataService;

    public void save(Cinema cinema) {
        cinemaDAO.save(cinema);
    }

    public Cinema get(long id) {
        return cinemaDAO.get(id);
    }

    public Cinema getByName(String name) {
        return cinemaDAO.getByName(name);
    }

    public void update(Cinema cinema) {
        cinemaDAO.update(cinema);
    }

    public void delete(Cinema cinema) {
        cinemaDAO.delete(cinema);
    }

    public List<Cinema> getAll() {
        return cinemaDAO.getAll();
    }

    public Advertising getAdvertising(String name) {
        try {
            Cinema c = getByName(name);
            return c.getAdvertising();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Set<Stock> getStocks(String name) {
        try {
            Cinema c = getByName(name);
            Hibernate.initialize(c.getStocks());
            return c.getStocks();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public CafeBar getCafeBar(String name) {
        try {
            return getByName(name).getCafeBar();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public ChildrensRoom getChildrensRoom(String name) {
        try {
            return getByName(name).getChildrensRoom();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Set<Image> getImages(String name) {
        try {
            Cinema c = getByName(name);
            Hibernate.initialize(c.getImages());
            return c.getImages();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Contacts getContacts(String name) {
        try {
            return getByName(name).getContacts();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Set<FilmData> getFilmsDataInCinema(String name) {
        Cinema c = getByName(name);
        try {
            Hibernate.initialize(c.getFilmsData());
            return c.getFilmsData();
        } catch (IllegalArgumentException e) {
            System.out.println("No cinema name: " + name + " in database ");
            return null;
        }
    }

    public Set<FilmHall> getFilmHalls(String name) {
        try {
            Cinema c = getByName(name);
            Hibernate.initialize(c.getFilmHalls());
            return c.getFilmHalls();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Map<Cinema, Set<FilmData>> getFilmsDataInAllCinemas() {
        try {
            Map<Cinema, Set<FilmData>> map = new LinkedHashMap<>();
            getAll().forEach(i -> map.put(i, getFilmsDataInCinema(i.getCinemaName())));
            return map;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public MobileApp getMobileApp(String name) {
        try {
            return getByName(name).getMobileApp();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public Set<News> getNews(String name) {
        try {
            Cinema c = getByName(name);
            Hibernate.initialize(c.getNews());
            return c.getNews();
        } catch (NullPointerException e) {
            System.out.println("No name: " + name);
            return null;
        }
    }

    public void deleteByName(String name) {
        Cinema c = getByName(name);
        delete(c);
    }

    public int[] getNumberOfFilmsInCinemaEveryMonth(String name) {
        try {
            List<CurrentFilmData> currentFilmData = getCurrentFilmsData(name);
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

    public int[][] getNumberOfFilmsInCinemasEveryMonth() {
        try {
            int k = 0;
            int[][] numbers = new int[cinemaDAO.getAll().size()][12];
            for (Cinema c : cinemaDAO.getAll()) {
                List<CurrentFilmData> currentFilmData = getCurrentFilmsData(c.getCinemaName());


                for (int i = 1; i <= 12; i++) {
                    for (CurrentFilmData currentFilmData1 : currentFilmData) {
                        LocalDateTime localDateTime = LocalDateTime.parse(
                                currentFilmData1.getFilmTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        );
                        if (localDateTime.getMonthValue() == i) numbers[k][i - 1] += 1;
                    }
                }
                k++;
            }
            return numbers;
        } catch (IllegalArgumentException | NoResultException e) {
            return new int[2][12];
        }
    }

    public List<String> getAllFilmsInCinema(String name) {
        try {
            List<FilmData> filmsData = getFilmsData(name);
            return filmsData.stream().
                    map(o -> ((FilmData) o).
                            getFilmTitle()).
                    collect(Collectors.toList());
        } catch (NullPointerException | NoResultException e) {
            System.out.println("no cinema: " + name);
            return null;
        }

    }

    public long[] getNumberOfUsersEveryMonth(String name) {
        try {
            List<CurrentFilmData> currentFilmData = getCurrentFilmsData(name);
            long[] numbers = new long[12];

            for (int i = 1; i <= 12; i++) {
                for (CurrentFilmData currentFilmData1 : currentFilmData) {
                    LocalDateTime localDateTime = LocalDateTime.parse(
                            currentFilmData1.getFilmTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    );
                    if (localDateTime.getMonthValue() == i) {
                        numbers[i - 1] += currentFilmDataService.getNumberOfCurrentFilmData(currentFilmData1.getId());
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
        try {
            List<CurrentFilmData> currentFilmData = getCurrentFilmsData(name);
            int[] numbers = new int[12];

            for (int i = 1; i <= 12; i++) {
                for (CurrentFilmData currentFilmData1 : currentFilmData) {
                    LocalDateTime localDateTime = LocalDateTime.parse(
                            currentFilmData1.getFilmTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    );
                    if (localDateTime.getMonthValue() == i) numbers[i - 1] +=
                            currentFilmDataService.getUsers(currentFilmData1.getId()).stream().
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

    public List<Integer> getNumberOfFilmData(String name) {
        return cinemaDAO.getNumberOfFilmData(name);
    }

    public List getCurrentFilmsData(String name) {
        return cinemaDAO.getCurrentFilmsData(name);
    }

    public List getFilmsData(String name) {
        return cinemaDAO.getFilmsData(name);
    }

    public List<String> getAllCinemasNames() {
        return getAll().stream().map(Cinema::getCinemaName).collect(Collectors.toList());
    }
}
