package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private long id;

    @Column(name = "news_title")
    @Size(min = 2, max = 200, message = "Incorrect size")
    private String newsTitle = "";

    @Column(name = "news_date")
    @NotNull(message = "Empty date")
    private LocalDate date;

    private String info = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return newsTitle.equals(news.newsTitle) && date.equals(news.date) && info.equals(news.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsTitle, date, info);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", newsTitle='" + newsTitle + '\'' +
                ", date=" + date +
                ", info='" + info + '\'' +
                ", cinema name ='" + cinema.getCinemaName() +
                "\'}";
    }
}
