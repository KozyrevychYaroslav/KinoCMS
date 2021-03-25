package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.TypeResolutionStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
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
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Empty date")
    private Calendar filmTime;

    private boolean is3D;
    private boolean isVip;
    private boolean isDBOX;

    @DecimalMin(value = "0", message = "Too small number")
    @DecimalMax(value = "5000", message = "Too big number")
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_hall_id", nullable = false)
    private FilmHall filmHall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_data_id", nullable = false)
    private FilmData filmData;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "CurrentFilmAndUser",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "current_film_data_id")
    )
    private Set<User> users;


}
