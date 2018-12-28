package com.github.jszeluga.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Set;
import java.util.function.Consumer;

public class HibernateTransaction {

    private static SessionFactory sessionFactory;

    public static void openSessionFactory(){
        Configuration config = new Configuration();
        Reflections reflections = ReflectionUtil.REFLECTIONS;
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        entities.forEach(config::addAnnotatedClass);

        sessionFactory = config.configure().buildSessionFactory();
    }

    public static void doWithSession(Consumer<Session> sessionFunc){
        if(sessionFunc!=null){
            try (Session session = sessionFactory.openSession()){
                Transaction transaction = session.beginTransaction();
                sessionFunc.accept(session);
                transaction.commit();
            }
        }
    }

    public static void closeSessionFactory(){
        sessionFactory.close();
    }

}
