package com.github.jszeluga;

import com.github.jszeluga.util.HibernateTransaction;

public class MainApp {

  public static void main(String[] args){

    //do stuff
    HibernateTransaction.closeSessionFactory();
  }
}
