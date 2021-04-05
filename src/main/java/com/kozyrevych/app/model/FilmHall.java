package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class FilmHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_hall_id")
    private long id;

    @Column(name = "film_hall_number")
    @Min(value = 1, message = "Too small number")
    @Max(value = 50, message = "Too big number")
    private int filmHallNumber;

    private String info = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filmHall", cascade = CascadeType.ALL)
    private Set<FreePlace> freePlaces;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filmHall", cascade = CascadeType.ALL)
    private Set<CurrentFilmData> currentFilmsData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmHall filmHall = (FilmHall) o;
        return filmHallNumber == filmHall.filmHallNumber && info.equals(filmHall.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmHallNumber, info);
    }

    @Override
    public String toString() {
        return "FilmHall{" +
                "id=" + id +
                ", filmHallNumber=" + filmHallNumber +
                ", info='" + info + '\'' +
                ", cinema name ='" + cinema.getCinemaName() +
                "'}";
    }
}
