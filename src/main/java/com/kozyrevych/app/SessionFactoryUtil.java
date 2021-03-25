package com.kozyrevych.app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory = null;

    public static synchronized SessionFactory getSessionFactory() {
        sessionFactory = sessionFactory == null ? new Configuration().configure().buildSessionFactory() : sessionFactory;
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        try {
            sessionFactory.close();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}
