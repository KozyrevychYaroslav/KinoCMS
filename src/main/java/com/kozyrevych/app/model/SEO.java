package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
public class SEO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seo_id")
    private long id;

    @Size(min = 1, max = 40, message = "Incorrect size")
    private String URL = "";

    @Size(max = 200, message = "Incorrect size")
    private String keywords= "";

    @Size(max = 200, message = "Incorrect size")
    private String title= "";

    private String description= "";

    @OneToOne(mappedBy = "seo", fetch = FetchType.EAGER)
    private FilmData filmData;
}
