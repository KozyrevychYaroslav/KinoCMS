package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
public class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long id;

    private String info = "";

    @Column(name = "image_path")
    @NotNull(message = "empty path")
    private String imagePath;

    @Column(name = "image_name")
    @Size(min = 1, max = 200, message = "Incorrect size")
    private String imageName = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;


}
