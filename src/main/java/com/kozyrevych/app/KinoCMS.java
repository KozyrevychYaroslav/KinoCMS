package com.kozyrevych.app;


import com.kozyrevych.app.dao.UserDAO;
import com.kozyrevych.app.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;


@SpringBootApplication
public class KinoCMS {

    public static void main(String[] args) {

        SpringApplication.run(KinoCMS.class, args);
    }
}
