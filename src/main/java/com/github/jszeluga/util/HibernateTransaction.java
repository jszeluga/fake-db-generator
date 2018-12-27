package com.github.jszeluga.util;


import com.github.jszeluga.entity.LteFact;
import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.entity.dimension.DispositionDimension;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

public class HibernateTransaction {

  private static SessionFactory sessionFactory;

  public static void openSessionFactory(){
    Configuration config = new Configuration();

    //Dimensions
    config.addAnnotatedClass(CellDimension.class);
    config.addAnnotatedClass(CustomerDimension.class);
    config.addAnnotatedClass(DeviceDimension.class);
    config.addAnnotatedClass(DispositionDimension.class);

    //Entities
    config.addAnnotatedClass(LteFact.class);

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
