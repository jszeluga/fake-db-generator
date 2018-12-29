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

/**
 * Helper class to handle
 * {@link SessionFactory}, {@link Session}, and {@link Transaction}
 */
public class HibernateTransaction {

    private static SessionFactory sessionFactory;

    /**
     * Opens the Hibernate {@link SessionFactory}
     * This should only be called once during application startup
     */
    public static void openSessionFactory(){
        Configuration config = new Configuration();
        Reflections reflections = new Reflections("com.github.jszeluga");
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        entities.forEach(config::addAnnotatedClass);

        sessionFactory = config.configure().buildSessionFactory();
    }

    /**
     * Provides a shell around the Hibernate transaction and calls the {@link Consumer} on the {@link Session}
     * @param sessionFunc the Consumer to apply to the Hibernate {@link Session}
     */
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

    /**
     * Provides a shell around the Hibernate {@link Transaction} and returns the result from the {@link Function}
     * @param sessionFunc The {@link Function} to apply on the Hibernate {@link Session}
     * @param <T> The return type
     * @return Object of type T
     */
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

    /**
     * Closes the Hibernate {@link SessionFactory}
     * This should only be called on application shutdown
     */
    public static void closeSessionFactory(){
        sessionFactory.close();
    }

}
