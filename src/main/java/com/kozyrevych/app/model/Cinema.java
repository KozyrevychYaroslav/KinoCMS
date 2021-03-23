package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Cinema {

    @Id
    @Column(name = "cinema_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "cinema_name", nullable = false, length = 45)
    String cinemaName;

    @Column(nullable = false)
    String info;

    @Column(nullable = false, length = 80)
    String address;
}
