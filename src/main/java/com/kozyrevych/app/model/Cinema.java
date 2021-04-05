package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL)
    private Set<Stock> stocks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL)
    private Set<FilmHall> filmHalls;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL)
    private Set<News> news;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "FilmCinema",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "film_data_id"))
    private Set<FilmData> filmsData;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cinema", cascade = CascadeType.ALL)
    private Contacts contacts;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cinema", cascade = CascadeType.ALL)
    private MobileApp mobileApp;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cinema", cascade = CascadeType.ALL)
    private ChildrensRoom childrensRoom;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cinema", cascade = CascadeType.ALL)
    private CafeBar cafeBar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema", cascade = CascadeType.ALL)
    private Set<Image> images;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cinema", cascade = CascadeType.ALL)
    private Advertising advertising;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return cinemaName.equals(cinema.cinemaName) && info.equals(cinema.info) && address.equals(cinema.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cinemaName, info, address);
    }

    public void removeFilmData(FilmData filmData) {
        filmsData.removeIf(o -> o.getId() == filmData.getId());
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", cinemaName='" + cinemaName + '\'' +
                ", info='" + info + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
