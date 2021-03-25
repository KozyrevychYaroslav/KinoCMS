package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Stock {

    @Column(name = "stock_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "stock_title")
    @Size(min = 2, max = 100, message = "Incorrect size")
    private String title = "";

    @Column(name = "stock_date")
    @NotNull(message = "Empty date")
    private LocalDate date;

    private String info = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return title.equals(stock.title) && date.equals(stock.date) && info.equals(stock.info) && cinema.equals(stock.cinema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, info, cinema);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date+
                ", info='" + info + '\'' +
                ", cinema name ='" + cinema.getCinemaName() +
                "\'}";
    }
}
