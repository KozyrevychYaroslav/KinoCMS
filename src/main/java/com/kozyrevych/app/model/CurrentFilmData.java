package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.TypeResolutionStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class CurrentFilmData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "current_film_data_id")
    private long id;

    @Column(name = "film_time")
    @NotNull(message = "Empty date")
    private String filmTime;

    private boolean is3D;
    private boolean isVip;
    private boolean isDBOX;

    @DecimalMin(value = "0", message = "Too small number")
    @DecimalMax(value = "5000", message = "Too big number")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_hall_id", nullable = false)
    private FilmHall filmHall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_data_id", nullable = false)
    private FilmData filmData;

    @ManyToMany(mappedBy = "currentFilmsData", fetch = FetchType.LAZY)
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentFilmData that = (CurrentFilmData) o;
        return is3D == that.is3D && isVip == that.isVip && isDBOX == that.isDBOX && Double.compare(that.price, price) == 0 && filmTime.equals(that.filmTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmTime, is3D, isVip, isDBOX, price);
    }

    @Override
    public String toString() {
        return "CurrentFilmData{" +
                "id=" + id +
                ", filmTime=" + filmTime +
                ", is3D=" + is3D +
                ", isVip=" + isVip +
                ", isDBOX=" + isDBOX +
                ", price=" + price +
                ", film title ='" + filmData.getFilmTitle() +
                "\'}";
    }
}
