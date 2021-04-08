package com.kozyrevych.app.controllers;

import com.kozyrevych.app.model.Image;
import com.kozyrevych.app.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class BannerController {
    private ImageService imageService;

    @Autowired
    public BannerController(ImageService imageService) {
        this.imageService = imageService;
        Image image = new Image();
        image.setImageName("main_img1");
        image.setImagePath("images/mainImages/main_img1.jpg");
        imageService.save(image);

        Image image1 = new Image();
        image1.setImageName("main_img2");
        image1.setImagePath("images/mainImages/main_img2.jpg");
        imageService.save(image1);

        Image image2 = new Image();
        image2.setImageName("fone_img1");
        image2.setImagePath("images/foneImages/fone_img1.jpg");
        imageService.save(image2);

        Image image3 = new Image();
        image3.setImageName("mainNewsStocks1");
        image3.setImagePath("images/mainNewsStocks/mainNewsStocks1.jpg");
        imageService.save(image3);
    }

    @GetMapping("banner")
    public String getBanner(Model model) {
        model.addAttribute("images", imageService.getAll().stream().filter(i -> i.getImagePath().
                startsWith("images/mainImages/")).toArray());
        model.addAttribute("foneImages", imageService.getAll().stream().
                filter(i -> i.getImagePath().startsWith("images/foneImages")).toArray());
        model.addAttribute("mainNewsStocksImages", imageService.getAll().stream().
                filter(i -> i.getImagePath().startsWith("images/mainNewsStocks")).toArray());
        return "banner";
    }

    @PostMapping("addPhoto")
    public String getPath(@RequestParam("fileVal") String fileVal) {
        String s = "images/mainImages/" + fileVal.substring(fileVal.lastIndexOf('\\') + 1);
        if (fileVal.lastIndexOf('\\') == -1) {
            return "redirect:/banner";
        }
        Image image = new Image();
        image.setImagePath(s);
        image.setImageName(s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf('.')));
        imageService.save(image);
        return "redirect:/banner";
    }

    @GetMapping("delete/{id}")
    public String deleteImage(@PathVariable("id") String name) {
        imageService.deleteByName(name);
        return "redirect:/banner";
    }

    @PostMapping("addFoneImage")
    public String addFoneImage(@RequestParam("fileVal1") String fileVal1) {
        if (fileVal1.lastIndexOf('\\') == -1) {
            return "redirect:/banner";
        }

        String s = "images/foneImages/" + fileVal1.substring(fileVal1.lastIndexOf('\\') + 1);

        for(Image image : imageService.getAll()) {
            if(image.getImagePath().startsWith("images/foneImages")) imageService.delete(image);
        }

        Image image = new Image();
        image.setImagePath(s);
        image.setImageName(s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf('.')));
        imageService.save(image);
        return "redirect:/banner";
    }

    @PostMapping("addNewsStocksImage")
    public String addNewsStocksImage(@RequestParam("fileVal2") String fileVal2) {
        if (fileVal2.lastIndexOf('\\') == -1) {
            return "redirect:/banner";
        }

        String s = "images/mainNewsStocks/" + fileVal2.substring(fileVal2.lastIndexOf('\\') + 1);

        Image image = new Image();
        image.setImagePath(s);
        image.setImageName(s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf('.')));
        imageService.save(image);
        return "redirect:/banner";
    }
}
