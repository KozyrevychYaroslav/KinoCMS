package com.kozyrevych.app.controllers;

import com.kozyrevych.app.model.*;
import com.kozyrevych.app.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatisticsController {

    private UserService userService;
    private CinemaService cinemaService;
    private FilmDataService filmDataService;
    private CurrentFilmDataService currentFilmDataService;
    private FilmHallService filmHallService;

    @Autowired
    public StatisticsController(UserService userService, CinemaService cinemaService, FilmDataService filmDataService, CurrentFilmDataService currentFilmDataService, FilmHallService filmHallService) {
        this.userService = userService;
        this.cinemaService = cinemaService;
        this.filmDataService = filmDataService;
        this.currentFilmDataService = currentFilmDataService;

        FilmData filmData = new FilmData();

        filmData.setBudget(125_000);
        filmData.setFilmTitle("Film title #1");
        filmData.setFilmLength("95 минут");
        filmData.setComposer("Composer #1");
        filmData.setCountry("Ukraine");
        filmData.setDirector("Director #1");
        filmData.setFilmDescription("Some description #1");
        filmData.setGenre("Horror");
        filmData.setMinimumAge(18);
        filmData.setProducer("Producer #1");
        filmData.setOperator("Operator #1");
        filmData.setYear(2021);

        filmDataService.save(filmData);

        FilmData filmData1 = new FilmData();

        filmData1.setBudget(125_000);
        filmData1.setFilmTitle("Film title #2");
        filmData1.setFilmLength("100 минут");
        filmData1.setComposer("Composer #2");
        filmData1.setCountry("USA");
        filmData1.setDirector("Director #2");
        filmData1.setFilmDescription("Some description #2");
        filmData1.setGenre("Horror");
        filmData1.setMinimumAge(18);
        filmData1.setProducer("Producer #2");
        filmData1.setOperator("Operator #2");
        filmData1.setYear(2021);

        filmDataService.save(filmData1);

        Cinema cinema = new Cinema();
        Cinema cinema1 = new Cinema();

        cinema.setAddress("Высоцкого 50/б");
        cinema.setCinemaName("Кинотеатр #1");
        cinema.setInfo("some info");

        cinema1.setAddress("Бочарова 60");
        cinema1.setCinemaName("Кинотеатр #2");
        cinema1.setInfo("some info2");

        FilmData filmData2 = new FilmData();

        filmData2.setBudget(125_000);
        filmData2.setFilmTitle("Film title #3");
        filmData2.setFilmLength("100 минут");
        filmData2.setComposer("Composer #3");
        filmData2.setCountry("USA");
        filmData2.setDirector("Director #3");
        filmData2.setFilmDescription("Some description #3");
        filmData2.setGenre("Horror");
        filmData2.setMinimumAge(18);
        filmData2.setProducer("Producer #3");
        filmData2.setOperator("Operator #3");
        filmData2.setYear(2021);
        filmDataService.save(filmData2);

//        cinemaService.setFilmsData(cinema, new LinkedHashSet<>(Arrays.asList(filmData, filmDataService.getByTitle("Film title #2"))));
//        cinemaService.setFilmsData(cinema1, new LinkedHashSet<>(Arrays.asList(filmDataService.getByTitle("Film title #2"), filmData2)));
        cinema.setFilmsData(new LinkedHashSet<>(Arrays.asList(filmData, filmDataService.getByTitle("Film title #2"))));
        cinema1.setFilmsData(new LinkedHashSet<>(Arrays.asList(filmDataService.getByTitle("Film title #2"), filmData2)));
        cinemaService.save(cinema);
        cinemaService.save(cinema1);
        FilmHall filmHall = new FilmHall();
        FilmHall filmHall1 = new FilmHall();
        FilmHall filmHall2 = new FilmHall();
        FilmHall filmHall3 = new FilmHall();

        filmHall.setCinema(cinema);
        filmHall.setInfo("Film hall №1 info");
        filmHall.setFilmHallNumber(10);

        filmHall1.setCinema(cinema1);
        filmHall1.setInfo("Film hall №2 info");
        filmHall1.setFilmHallNumber(10);

        filmHall2.setCinema(cinema);
        filmHall2.setInfo("Film hall №3 info");
        filmHall2.setFilmHallNumber(10);

        filmHall3.setCinema(cinema1);
        filmHall3.setInfo("Film hall №4 info");
        filmHall3.setFilmHallNumber(10);

        cinema.setFilmHalls(new LinkedHashSet<>(Arrays.asList(filmHall, filmHall2)));
        cinema1.setFilmHalls(new LinkedHashSet<>(Arrays.asList(filmHall1, filmHall3)));

        cinemaService.save(cinema);
        cinemaService.save(cinema1);

        //Cinema #1
        CurrentFilmData currentFilmData = new CurrentFilmData();
        currentFilmData.setFilmTime(LocalDateTime.of(2021, Month.JANUARY, 20, 22, 32).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData.setDBOX(false);
        currentFilmData.set3D(false);
        currentFilmData.setDBOX(true);
        currentFilmData.setPrice(150);
        currentFilmData.setVip(false);
        currentFilmData.setFilmData(filmDataService.getByTitle("Film title #1"));
        currentFilmData.setFilmHall(filmHall);

        CurrentFilmData currentFilmData1 = new CurrentFilmData();
        currentFilmData1.setFilmTime(LocalDateTime.of(2021, Month.JANUARY, 21, 22, 31).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData1.setDBOX(true);
        currentFilmData1.set3D(false);
        currentFilmData1.setDBOX(true);
        currentFilmData1.setPrice(160);
        currentFilmData1.setVip(true);
        currentFilmData1.setFilmData(filmDataService.getByTitle("Film title #2"));
        currentFilmData1.setFilmHall(filmHall1);

        //Cinema #1
        CurrentFilmData currentFilmData2 = new CurrentFilmData();
        currentFilmData2.setFilmTime(LocalDateTime.of(2021, Month.JANUARY, 20, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData2.setDBOX(true);
        currentFilmData2.set3D(false);
        currentFilmData2.setDBOX(true);
        currentFilmData2.setPrice(195);
        currentFilmData2.setVip(false);
        currentFilmData2.setFilmHall(filmHall2);
        currentFilmData2.setFilmData(filmDataService.getByTitle("Film title #2"));

        CurrentFilmData currentFilmData3 = new CurrentFilmData();
        currentFilmData3.setFilmTime(LocalDateTime.of(2021, Month.FEBRUARY, 20, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData3.setDBOX(true);
        currentFilmData3.set3D(false);
        currentFilmData3.setDBOX(true);
        currentFilmData3.setPrice(190);
        currentFilmData3.setVip(false);
        currentFilmData3.setFilmHall(filmHall3);
        currentFilmData3.setFilmData(filmDataService.getByTitle("Film title #3"));

        //Cinema #1
        CurrentFilmData currentFilmData4 = new CurrentFilmData();
        currentFilmData4.setFilmTime(LocalDateTime.of(2021, Month.JANUARY, 20, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData4.setDBOX(true);
        currentFilmData4.set3D(false);
        currentFilmData4.setDBOX(true);
        currentFilmData4.setPrice(280);
        currentFilmData4.setVip(true);
        currentFilmData4.setFilmHall(filmHall);
        currentFilmData4.setFilmData(filmDataService.getByTitle("Film title #1"));

        CurrentFilmData currentFilmData5 = new CurrentFilmData();
        currentFilmData5.setFilmTime(LocalDateTime.of(2021, Month.FEBRUARY, 14, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData5.setDBOX(true);
        currentFilmData5.set3D(true);
        currentFilmData5.setDBOX(true);
        currentFilmData5.setPrice(300);
        currentFilmData5.setVip(false);
        currentFilmData5.setFilmData(filmDataService.getByTitle("Film title #2"));
        currentFilmData5.setFilmHall(filmHall1);

        //Cinema #1
        CurrentFilmData currentFilmData6 = new CurrentFilmData();
        currentFilmData6.setFilmTime(LocalDateTime.of(2021, Month.FEBRUARY, 26, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData6.setDBOX(true);
        currentFilmData6.set3D(false);
        currentFilmData6.setDBOX(true);
        currentFilmData6.setPrice(320);
        currentFilmData6.setVip(false);
        currentFilmData6.setFilmData(filmDataService.getByTitle("Film title #2"));
        currentFilmData6.setFilmHall(filmHall2);

        CurrentFilmData currentFilmData7 = new CurrentFilmData();
        currentFilmData7.setFilmTime(LocalDateTime.of(2021, Month.MARCH, 7, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData7.setDBOX(true);
        currentFilmData7.set3D(true);
        currentFilmData7.setDBOX(true);
        currentFilmData7.setPrice(110);
        currentFilmData7.setVip(false);
        currentFilmData7.setFilmData(filmDataService.getByTitle("Film title #3"));
        currentFilmData7.setFilmHall(filmHall3);

        //Cinema #1
        CurrentFilmData currentFilmData8 = new CurrentFilmData();
        currentFilmData8.setFilmTime(LocalDateTime.of(2021, Month.MARCH, 6, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData8.setDBOX(true);
        currentFilmData8.set3D(false);
        currentFilmData8.setDBOX(true);
        currentFilmData8.setPrice(100);
        currentFilmData8.setVip(false);
        currentFilmData8.setFilmHall(filmHall);
        currentFilmData8.setFilmData(filmDataService.getByTitle("Film title #1"));

        CurrentFilmData currentFilmData9 = new CurrentFilmData();
        currentFilmData9.setFilmTime(LocalDateTime.of(2021, Month.MAY, 5, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData9.setDBOX(true);
        currentFilmData9.set3D(false);
        currentFilmData9.setDBOX(true);
        currentFilmData9.setPrice(90);
        currentFilmData9.setVip(false);
        currentFilmData9.setFilmData(filmDataService.getByTitle("Film title #2"));
        currentFilmData9.setFilmHall(filmHall1);

        //Cinema #1
        CurrentFilmData currentFilmData10 = new CurrentFilmData();
        currentFilmData10.setFilmTime(LocalDateTime.of(2021, Month.MAY, 4, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData10.setDBOX(true);
        currentFilmData10.set3D(false);
        currentFilmData10.setDBOX(true);
        currentFilmData10.setPrice(80);
        currentFilmData10.setVip(false);
        currentFilmData10.setFilmHall(filmHall2);
        currentFilmData10.setFilmData(filmDataService.getByTitle("Film title #2"));

        CurrentFilmData currentFilmData11 = new CurrentFilmData();
        currentFilmData11.setFilmTime(LocalDateTime.of(2021, Month.JUNE, 3, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData11.setDBOX(true);
        currentFilmData11.set3D(false);
        currentFilmData11.setDBOX(true);
        currentFilmData11.setPrice(70);
        currentFilmData11.setVip(false);
        currentFilmData11.setFilmHall(filmHall3);
        currentFilmData11.setFilmData(filmDataService.getByTitle("Film title #3"));

        //Cinema #1
        CurrentFilmData currentFilmData12 = new CurrentFilmData();
        currentFilmData12.setFilmTime(LocalDateTime.of(2021, Month.JULY, 2, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData12.setDBOX(true);
        currentFilmData12.set3D(false);
        currentFilmData12.setDBOX(true);
        currentFilmData12.setPrice(60);
        currentFilmData12.setVip(false);
        currentFilmData12.setFilmHall(filmHall);
        currentFilmData12.setFilmData(filmDataService.getByTitle("Film title #1"));

        CurrentFilmData currentFilmData13 = new CurrentFilmData();
        currentFilmData13.setFilmTime(LocalDateTime.of(2021, Month.JUNE, 1, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData13.setDBOX(true);
        currentFilmData13.set3D(false);
        currentFilmData13.setDBOX(true);
        currentFilmData13.setPrice(50);
        currentFilmData13.setVip(false);
        currentFilmData13.setFilmHall(filmHall1);
        currentFilmData13.setFilmData(filmDataService.getByTitle("Film title #2"));

        //Cinema #1
        CurrentFilmData currentFilmData14 = new CurrentFilmData();
        currentFilmData14.setFilmTime(LocalDateTime.of(2021, Month.JULY, 25, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData14.setDBOX(true);
        currentFilmData14.set3D(false);
        currentFilmData14.setDBOX(true);
        currentFilmData14.setPrice(190);
        currentFilmData14.setVip(false);
        currentFilmData14.setFilmHall(filmHall2);
        currentFilmData14.setFilmData(filmDataService.getByTitle("Film title #1"));

        CurrentFilmData currentFilmData15 = new CurrentFilmData();
        currentFilmData15.setFilmTime(LocalDateTime.of(2021, Month.JUNE, 11, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData15.setDBOX(true);
        currentFilmData15.set3D(false);
        currentFilmData15.setDBOX(true);
        currentFilmData15.setPrice(80);
        currentFilmData15.setVip(false);
        currentFilmData15.setFilmHall(filmHall3);
        currentFilmData15.setFilmData(filmDataService.getByTitle("Film title #3"));

        //Cinema #1
        CurrentFilmData currentFilmData16 = new CurrentFilmData();
        currentFilmData16.setFilmTime(LocalDateTime.of(2021, Month.JULY, 6, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData16.setDBOX(true);
        currentFilmData16.set3D(false);
        currentFilmData16.setDBOX(true);
        currentFilmData16.setPrice(90);
        currentFilmData16.setVip(false);
        currentFilmData16.setFilmHall(filmHall);
        currentFilmData16.setFilmData(filmDataService.getByTitle("Film title #1"));

        CurrentFilmData currentFilmData17 = new CurrentFilmData();
        currentFilmData17.setFilmTime(LocalDateTime.of(2021, Month.SEPTEMBER, 5, 22, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData17.setDBOX(true);
        currentFilmData17.set3D(false);
        currentFilmData17.setDBOX(true);
        currentFilmData17.setPrice(110);
        currentFilmData17.setVip(false);
        currentFilmData17.setFilmHall(filmHall1);
        currentFilmData17.setFilmData(filmDataService.getByTitle("Film title #2"));

        //Cinema #1
        CurrentFilmData currentFilmData18 = new CurrentFilmData();
        currentFilmData18.setFilmTime(LocalDateTime.of(2021, Month.SEPTEMBER, 20, 12, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData18.setDBOX(true);
        currentFilmData18.set3D(false);
        currentFilmData18.setDBOX(true);
        currentFilmData18.setPrice(100);
        currentFilmData18.setVip(false);
        currentFilmData18.setFilmHall(filmHall2);
        currentFilmData18.setFilmData(filmDataService.getByTitle("Film title #2"));

        CurrentFilmData currentFilmData19 = new CurrentFilmData();
        currentFilmData19.setFilmTime(LocalDateTime.of(2021, Month.SEPTEMBER, 19, 23, 30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData19.setDBOX(true);
        currentFilmData19.set3D(false);
        currentFilmData19.setDBOX(true);
        currentFilmData19.setPrice(250);
        currentFilmData19.setVip(false);
        currentFilmData19.setFilmHall(filmHall3);
        currentFilmData19.setFilmData(filmDataService.getByTitle("Film title #3"));

        //Cinema #1
        CurrentFilmData currentFilmData20 = new CurrentFilmData();
        currentFilmData20.setFilmTime(LocalDateTime.of(2021, Month.SEPTEMBER, 18, 22, 31).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData20.setDBOX(true);
        currentFilmData20.set3D(true);
        currentFilmData20.setDBOX(true);
        currentFilmData20.setPrice(220);
        currentFilmData20.setVip(false);
        currentFilmData20.setFilmHall(filmHall);
        currentFilmData20.setFilmData(filmDataService.getByTitle("Film title #1"));

        User user = new User();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();
        User user7 = new User();
        User user8 = new User();
        User user9 = new User();
        User user10 = new User();
        User user11 = new User();


        user.setBirthday(LocalDate.of(2001, 6, 15));
        user.setEmail("user1@mailru");
        user.setName("name 1");
        user.setPassword("password 1");
        user.setPhoneNumber("+380677157636");
        user.setRegistrationDate(LocalDate.now());
        user.setGender(Gender.MALE);
        user.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData, currentFilmData6, currentFilmData2, currentFilmData3,  currentFilmData10,  currentFilmData7)));
        userService.save(user);

        user1.setBirthday(LocalDate.of(2000, 4, 15));
        user1.setEmail("user2@mailru");
        user1.setName("name 2");
        user1.setPassword("password 2");
        user1.setPhoneNumber("+380677157655");
        user1.setRegistrationDate(LocalDate.now());
        user1.setGender(Gender.MALE);
        user1.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData2, currentFilmData1, currentFilmData3, currentFilmData4,  currentFilmData20, currentFilmData18)));
        userService.save(user1);

        user2.setBirthday(LocalDate.of(2000, 4, 15));
        user2.setEmail("user3@mailru");
        user2.setName("name 3");
        user2.setPassword("password 3");
        user2.setPhoneNumber("+380677157654");
        user2.setRegistrationDate(LocalDate.now());
        user2.setGender(Gender.FEMALE);
        user2.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData3, currentFilmData4, currentFilmData5,  currentFilmData15,  currentFilmData16)));
        userService.save(user2);

        user3.setBirthday(LocalDate.of(2000, 4, 15));
        user3.setEmail("user4@mailru");
        user3.setName("name 4");
        user3.setPassword("password 4");
        user3.setPhoneNumber("+380677157653");
        user3.setRegistrationDate(LocalDate.now());
        user3.setGender(Gender.MALE);
        user3.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData4, currentFilmData5, currentFilmData6,  currentFilmData12,  currentFilmData13)));
        userService.save(user3);

        user4.setBirthday(LocalDate.of(2000, 4, 15));
        user4.setEmail("user5@mailru");
        user4.setName("name 5");
        user4.setPassword("password 5");
        user4.setPhoneNumber("+380677157652");
        user4.setRegistrationDate(LocalDate.now());
        user4.setGender(Gender.FEMALE);
        user4.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData5, currentFilmData6, currentFilmData7,  currentFilmData11,  currentFilmData10)));
        userService.save(user4);

        user5.setBirthday(LocalDate.of(2000, 4, 15));
        user5.setEmail("user6@mailru");
        user5.setName("name 6");
        user5.setPassword("password 6");
        user5.setPhoneNumber("+380677157651");
        user5.setRegistrationDate(LocalDate.now());
        user5.setGender(Gender.MALE);
        user5.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData6, currentFilmData7, currentFilmData8,  currentFilmData19,  currentFilmData17)));
        userService.save(user5);

        user6.setBirthday(LocalDate.of(2000, 4, 15));
        user6.setEmail("user7@mailru");
        user6.setName("name 7");
        user6.setPassword("password 7");
        user6.setPhoneNumber("+380677157650");
        user6.setRegistrationDate(LocalDate.now());
        user6.setGender(Gender.FEMALE);
        user6.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData7, currentFilmData8, currentFilmData9,  currentFilmData18,  currentFilmData20)));
        userService.save(user6);

        user7.setBirthday(LocalDate.of(2000, 4, 15));
        user7.setEmail("user8@mailru");
        user7.setName("name 8");
        user7.setPassword("password 8");
        user7.setPhoneNumber("+380677157649");
        user7.setRegistrationDate(LocalDate.now());
        user7.setGender(Gender.MALE);
        user7.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData8, currentFilmData9, currentFilmData10,  currentFilmData17,  currentFilmData16)));
        userService.save(user7);

        user8.setBirthday(LocalDate.of(2000, 4, 15));
        user8.setEmail("user9@mailru");
        user8.setName("name 9");
        user8.setPassword("password 9");
        user8.setPhoneNumber("+380677157648");
        user8.setRegistrationDate(LocalDate.now());
        user8.setGender(Gender.FEMALE);
        user8.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData9, currentFilmData10, currentFilmData11,  currentFilmData15,  currentFilmData13)));
        userService.save(user8);

        user9.setBirthday(LocalDate.of(2000, 4, 15));
        user9.setEmail("user10@mailru");
        user9.setName("name 10");
        user9.setPassword("password 10");
        user9.setPhoneNumber("+380677157647");
        user9.setRegistrationDate(LocalDate.now());
        user9.setGender(Gender.MALE);
        user9.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData10, currentFilmData11, currentFilmData12,  currentFilmData13,  currentFilmData14)));
        userService.save(user9);

        user10.setBirthday(LocalDate.of(2000, 4, 15));
        user10.setEmail("user11@mailru");
        user10.setName("name 11");
        user10.setPassword("password 11");
        user10.setPhoneNumber("+380677157645");
        user10.setRegistrationDate(LocalDate.now());
        user10.setGender(Gender.FEMALE);

        user11.setBirthday(LocalDate.of(2000, 4, 15));
        user11.setEmail("user12@mailru");
        user11.setName("name 12");
        user11.setPassword("password 12");
        user11.setPhoneNumber("+380677157643");
        user11.setRegistrationDate(LocalDate.now());
        user11.setGender(Gender.FEMALE);

        FilmData filmData3 = new FilmData();

        filmData3.setBudget(125_000);
        filmData3.setFilmTitle("Film title #4");
        filmData3.setFilmLength("95 минут");
        filmData3.setComposer("Composer #4");
        filmData3.setCountry("Ukraine");
        filmData3.setDirector("Director #4");
        filmData3.setFilmDescription("Some description #4");
        filmData3.setGenre("Horror");
        filmData3.setMinimumAge(18);
        filmData3.setProducer("Producer #4");
        filmData3.setOperator("Operator #4");
        filmData3.setYear(2021);
        filmDataService.save(filmData3);
        cinema.setFilmsData(new LinkedHashSet<>(Arrays.asList(filmData3)));
        cinemaService.save(cinema);

        //Cinema #1
        CurrentFilmData currentFilmData21 = new CurrentFilmData();
        currentFilmData21.setFilmTime(LocalDateTime.of(2021, Month.DECEMBER, 20, 22, 19).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData21.setDBOX(true);
        currentFilmData21.set3D(false);
        currentFilmData21.setDBOX(true);
        currentFilmData21.setPrice(280);
        currentFilmData21.setVip(false);
        currentFilmData21.setFilmHall(filmHall);
        currentFilmData21.setFilmData(filmDataService.getByTitle("Film title #4"));
        currentFilmDataService.save(currentFilmData21);

        //Cinema #1
        CurrentFilmData currentFilmData22 = new CurrentFilmData();
        currentFilmData22.setFilmTime(LocalDateTime.of(2021, Month.DECEMBER, 20, 22, 27).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData22.setDBOX(true);
        currentFilmData22.set3D(false);
        currentFilmData22.setDBOX(true);
        currentFilmData22.setPrice(260);
        currentFilmData22.setVip(false);
        currentFilmData22.setFilmHall(filmHall2);
        currentFilmData22.setFilmData(filmDataService.getByTitle("Film title #4"));
        currentFilmDataService.save(currentFilmData22);

        //Cinema #1
        CurrentFilmData currentFilmData23 = new CurrentFilmData();
        currentFilmData23.setFilmTime(LocalDateTime.of(2021, Month.DECEMBER, 21, 22, 28).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        currentFilmData23.setDBOX(true);
        currentFilmData23.set3D(false);
        currentFilmData23.setDBOX(true);
        currentFilmData23.setPrice(240);
        currentFilmData23.setVip(false);
        currentFilmData23.setFilmHall(filmHall);
        currentFilmData23.setFilmData(filmDataService.getByTitle("Film title #4"));
        currentFilmDataService.save(currentFilmData23);

        user10.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData21, currentFilmData23)));
        user11.setCurrentFilmsData(new LinkedHashSet<>(Arrays.asList(currentFilmData22, currentFilmData23)));
        userService.save(user11);
        userService.save(user10);
    }

    @GetMapping("statistics")
    public String getStatistics(Model model) {
        model.addAttribute("registeredUsers", userService.getAll().size());
        model.addAttribute("films", cinemaService.getAllFilmsInCinema(cinemaService.getAll().get(0).getCinemaName()));
        model.addAttribute("filmsCount", cinemaService.getNumberOfFilmData(cinemaService.getAll().get(0).getCinemaName()));
        model.addAttribute("cinemas", cinemaService.getAllCinemasNames());
        model.addAttribute("cinemasPopularity", cinemaService.getNumberOfFilmsInCinemasEveryMonth());
        Map<Gender, Long> map = userService.getNumberOfGenders();
        model.addAttribute("genders", new long[]{map.get(Gender.MALE), map.get(Gender.FEMALE)});
        model.addAttribute("usersStat", cinemaService.getNumberOfUsersEveryMonth(cinemaService.getAll().get(0).getCinemaName()));
        model.addAttribute("profit", cinemaService.getProfit(cinemaService.getAll().get(0).getCinemaName()));
        model.addAttribute("profit1", cinemaService.getProfit(cinemaService.getAll().get(1).getCinemaName()));
        return "statistics";
    }

    @GetMapping("statistics1")
    public String getStatistics1(Model model) {
        model.addAttribute("registeredUsers", userService.getAll().size());
        model.addAttribute("films", cinemaService.getAllFilmsInCinema(cinemaService.getAll().get(1).getCinemaName()));
        model.addAttribute("filmsCount", cinemaService.getNumberOfFilmData(cinemaService.getAll().get(1).getCinemaName()));
        model.addAttribute("cinemas", cinemaService.getAllCinemasNames());
        model.addAttribute("cinemasPopularity", cinemaService.getNumberOfFilmsInCinemasEveryMonth());
        Map<Gender, Long> map = userService.getNumberOfGenders();
        model.addAttribute("genders", new long[]{map.get(Gender.MALE), map.get(Gender.FEMALE)});
        model.addAttribute("usersStat", cinemaService.getNumberOfUsersEveryMonth(cinemaService.getAll().get(1).getCinemaName()));
        model.addAttribute("profit", cinemaService.getProfit(cinemaService.getAll().get(0).getCinemaName()));
        model.addAttribute("profit1", cinemaService.getProfit(cinemaService.getAll().get(1).getCinemaName()));
        return "statistics";
    }
}
