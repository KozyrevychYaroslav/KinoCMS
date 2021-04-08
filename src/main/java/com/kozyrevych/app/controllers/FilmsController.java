package com.kozyrevych.app.controllers;

import com.kozyrevych.app.model.FilmData;
import com.kozyrevych.app.model.Image;
import com.kozyrevych.app.services.FilmDataService;
import com.kozyrevych.app.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FilmsController {
    private FilmDataService filmDataService;
    private ImageService imageService;

    @Autowired
    public FilmsController(FilmDataService filmDataService, ImageService imageService) {
        this.filmDataService = filmDataService;
        this.imageService = imageService;

        Image image = new Image();
        image.setImageName("filmMainImage1");
        image.setImagePath("images/filmsImages/filmMainImage1.jpg");
        imageService.save(image);
    }

    @GetMapping("filmPage")
    public String getFilmPage(@RequestParam String filmName, Model model) {
        model.addAttribute("filmMainImages", imageService.getAll().stream().filter(i -> i.getImagePath().
                startsWith("images/filmsImages/")).toArray());
        model.addAttribute("filmName", filmName);
        return "filmPage";
    }

    @PostMapping("addFilmMainImage/{filmName}")
    public String addFilmsImage(@RequestParam("fileVal1") String fileVal1, @PathVariable String filmName) {
        if (fileVal1.lastIndexOf('\\') == -1) {
            return "redirect:/filmPage?filmName=filmName";
        }

        for(Image image : imageService.getAll()) {
            if(image.getImagePath().startsWith("images/filmsImages")) imageService.delete(image);
        }

        String s = "images/filmsImages/" + fileVal1.substring(fileVal1.lastIndexOf('\\') + 1);

        Image image = new Image();
        image.setImagePath(s);
        image.setImageName(s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf('.')));
        imageService.save(image);
        return "redirect:/filmPage?filmName=filmName";
    }

    @GetMapping("deleteFilm/{id}/{filmName}")
    public String deleteImage(@PathVariable String filmName, @PathVariable String id) {
        imageService.deleteByName(id);
        return "redirect:/filmPage?filmName=filmName";
    }
}
