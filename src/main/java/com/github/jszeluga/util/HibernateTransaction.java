package com.github.jszeluga.util;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateTransaction {

    private static SessionFactory sessionFactory;

    public static void openSessionFactory(){
        Configuration config = new Configuration();
        Reflections reflections = new Reflections("com.github.jszeluga");
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        entities.forEach(config::addAnnotatedClass);

        sessionFactory = config.configure().buildSessionFactory();
    }

    public static void doWithSession(Consumer<Session> sessionFunc){
        if(sessionFunc!=null){
            Transaction transaction = null;
            Session session = sessionFactory.getCurrentSession();
            try {
                transaction = session.beginTransaction();
                sessionFunc.accept(session);
                transaction.commit();
            } catch (HibernateException e){
                if(transaction != null){
                    transaction.rollback();
                }

                throw e;
            }
        }
    }

    public static <T> T doWithSession(Function<Session, T> sessionFunc){
        if(sessionFunc != null){
            Transaction transaction = null;
            Session session = sessionFactory.getCurrentSession();
            try{
                transaction = session.beginTransaction();
                T ret = sessionFunc.apply(session);
                transaction.commit();
                return ret;
            } catch (HibernateException e){
                if(transaction != null){
                    transaction.rollback();
                }

                throw e;
            }
        }

        return null;
    }

    public static void closeSessionFactory(){
        sessionFactory.close();
    }

}
