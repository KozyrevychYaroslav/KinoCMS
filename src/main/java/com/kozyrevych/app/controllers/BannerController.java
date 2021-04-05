package com.kozyrevych.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BannerController {
    @GetMapping("banner")
    public String getBanner() {
        return "banner";
    }
}
