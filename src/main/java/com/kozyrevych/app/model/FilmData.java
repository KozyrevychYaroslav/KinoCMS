package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;
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
    private String director = "";

    @Size(min = 2, max = 200, message = "Incorrect size")
    private String composer = "";

    private boolean is3D;
    private boolean isVip;
    private boolean isDBOX;

    @Size(min = 2, max = 200, message = "Incorrect size")
    private String producer = "";

    @Size(min = 2, max = 200, message = "Incorrect size")
    private String operator = "";

    @Size(min = 2, max = 150, message = "Incorrect size")
    private String genre = "";

    @Column(name = "film_length")
    @Size(min = 2, max = 45, message = "Incorrect size")
    private String filmLength = "";

    @Column(name = "film_title")
    @Size(min = 2, max = 200, message = "Incorrect size")
    private String filmTitle = "";

    @Min(value = 50_000, message = "Incorrect budget")
    private int budget;

    @Min(value = 0, message = "Incorrect minimum age")
    @Max(value = 100, message = "Incorrect minimum age")
    @Column(name = "minimum_age")
    private int minimumAge;

    @Size(min = 10, message = "Incorrect size")
    @Column(name = "film_description")
    private String filmDescription = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filmData", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurrentFilmData> currentFilmData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filmData", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @ManyToMany(mappedBy = "filmsData", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Cinema> cinemas;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seo_id")
    private SEO seo;

    @PreRemove
    public void removeFilmDataFromCinemas() {
        cinemas.forEach(c -> c.removeFilmData(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmData filmData = (FilmData) o;
        return year == filmData.year && budget == filmData.budget && minimumAge == filmData.minimumAge && country.equals(filmData.country) && director.equals(filmData.director) && composer.equals(filmData.composer) && producer.equals(filmData.producer) && operator.equals(filmData.operator) && genre.equals(filmData.genre) && filmLength.equals(filmData.filmLength) && filmTitle.equals(filmData.filmTitle) && filmDescription.equals(filmData.filmDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, country, director, composer, producer, operator, genre, filmLength, filmTitle, budget, minimumAge, filmDescription);
    }

    @Override
    public String toString() {
        return "FilmData{" +
                "id=" + id +
                ", year=" + year +
                ", country='" + country + '\'' +
                ", director='" + director + '\'' +
                ", composer='" + composer + '\'' +
                ", producer='" + producer + '\'' +
                ", operator='" + operator + '\'' +
                ", genre='" + genre + '\'' +
                ", filmLength='" + filmLength + '\'' +
                ", filmTitle='" + filmTitle + '\'' +
                ", budget=" + budget +
                ", minimumAge=" + minimumAge +
                ", filmDescription='" + filmDescription + '\'' +
                '}';
    }
}
