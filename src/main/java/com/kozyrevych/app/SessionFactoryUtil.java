package com.kozyrevych.app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class SessionFactoryUtil {
    private final SessionFactory sessionFactory;

    public SessionFactoryUtil() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public void closeSessionFactory() {
        try {
            sessionFactory.close();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}
