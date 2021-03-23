package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Stock {

    @Column(name = "stock_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "stock_title", nullable = false, length = 100)
    String title;

    @Column(name = "stock_date", nullable = false)
    @Temporal(TemporalType.DATE)
    Date date;

    @Column(nullable = false)
    String info;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

}
