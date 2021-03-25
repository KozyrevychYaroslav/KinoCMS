package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class CafeBar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_bar_id")
    private long id;

    private String info = "";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @Override
    public String toString() {
        return "CafeBar{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", cinema name ='" + cinema.getCinemaName() +
                "\'}";
    }
}
