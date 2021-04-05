package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class FreePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_place_id")
    private long id;

    private boolean isBooked;

    @Column(name = "place_number")
    @Min(value = 1, message = "Too small number")
    @Max(value = 50, message = "Too big number")
    private int placeNumber;

    @Column(name = "row_num")
    @Min(value = 1, message = "Too small number")
    @Max(value = 50, message = "Too big number")
    private int rowNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_hall_id")
    private FilmHall filmHall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FreePlace freePlace = (FreePlace) o;
        return isBooked == freePlace.isBooked && placeNumber == freePlace.placeNumber && rowNumber == freePlace.rowNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isBooked, placeNumber, rowNumber);
    }

    @Override
    public String toString() {
        return "FreePlace{" +
                "id=" + id +
                ", isBooked=" + isBooked +
                ", placeNumber=" + placeNumber +
                ", rowNumber=" + rowNumber +
                '}';
    }
}
