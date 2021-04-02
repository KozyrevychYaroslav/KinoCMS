package com.kozyrevych.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class KinoCMS {
    public static void main(String[] args) {
        SpringApplication.run(KinoCMS.class, args);
    }
}
