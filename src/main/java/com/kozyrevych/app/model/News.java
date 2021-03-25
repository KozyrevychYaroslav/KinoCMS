package com.kozyrevych.app.model;

import jdk.jfr.Enabled;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Empty date")
    private Date date;

    private String info = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;
}
