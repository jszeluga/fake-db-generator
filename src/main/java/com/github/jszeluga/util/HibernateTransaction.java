package com.github.jszeluga.util;


import com.github.jszeluga.entity.dimension.CellDimension;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

public class HibernateTransaction {

  private static final Configuration config = new Configuration();

  static {
    //Dimensions
    config.addAnnotatedClass(CellDimension.class);
//    config.addAnnotatedClass(CustomerDimension.class);
//    config.addAnnotatedClass(DeviceDimension.class);

    //Entities
//    config.addAnnotatedClass(LteFact.class);
  }


  public static void doWithSession(Consumer<Session> sessionFunc){
    if(sessionFunc!=null){
      try (SessionFactory sessionFactory = config.configure().buildSessionFactory();
            Session session = sessionFactory.openSession()){
        Transaction transaction = session.beginTransaction();
        sessionFunc.accept(session);
        transaction.commit();
      }
    }
  }

}
