package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class FilmData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_data_id")
    private long id;

    @Min(value = 2020, message = "Incorrect year")
    @Max(value = 3000, message = "Incorrect year")
    private int year;

    @Size(min = 2, max = 100, message = "Incorrect size")
    private String country = "";

    @Size(min = 2, max = 200, message = "Incorrect size")
    private String composer = "";

    @Size(min = 2, max = 200, message = "Incorrect size")
    private String producer = "";

    @Size(min = 2, max = 200, message = "Incorrect size")
    private String operator = "";

    @Size(min = 2, max = 100, message = "Incorrect size")
    private String genre = "";

    @Column(name = "film_length")
    @Size(min = 2, max = 45, message = "Incorrect size")
    private String filmLength = "";

    @Column(name = "filmTitle")
    @Size(min = 2, max = 200, message = "Incorrect size")
    private String filmTitle = "";

    @Min(value = 50_000, message = "Incorrect budget")
    private int budget;

    @Min(value = 0, message = "Incorrect minimum age")
    @Column(name = "minimum_age")
    private int minimumAge;

    @Size(min = 10, message = "Incorrect size")
    @Column(name = "film_description")
    private String filmDescription = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filmData", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurrentFilmData> currentFilmData;

    @ManyToMany(mappedBy = "filmsData", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private Set<Cinema> cinemas;
}
