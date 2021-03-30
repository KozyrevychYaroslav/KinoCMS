package com.kozyrevych.app;


import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/statistics")
    public String test() {
        return  "statistics";
    }
}
