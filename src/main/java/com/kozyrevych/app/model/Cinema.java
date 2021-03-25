package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Cinema {
    @Id
    @Column(name = "cinema_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cinema_name")
    @Size(min = 5, max = 45, message = "Incorrect size")
    private String cinemaName = "";

    private String info = "";

    @Size(min = 5, max = 80, message = "Incorrect size")
    private String address = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Stock> stocks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilmHall> filmHalls;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<News> news;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "FilmCinema",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "film_data_id"))
    private Set<FilmData> filmsData;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contacts contacts;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private MobileApp mobileApp;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChildrensRoom childrensRoom;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private CafeBar cafeBar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Advertising advertising;
}
