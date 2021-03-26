package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long id;

    @Column(name = "image_path")
    @NotNull(message = "empty path")
    private String imagePath;

    @Column(name = "image_name")
    @Size(min = 1, max = 200, message = "Incorrect size")
    private String imageName = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", imageName='" + imageName + '\'' +
                ", cinema name ='" + cinema.getCinemaName() +
                "\'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return imagePath.equals(image.imagePath) && imageName.equals(image.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imagePath, imageName);
    }
}
