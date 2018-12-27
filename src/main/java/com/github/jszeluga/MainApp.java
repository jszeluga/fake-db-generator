package com.github.jszeluga;

import com.github.jszeluga.util.HibernateTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainApp {

  public static void main(String[] args){

    //do stuff
    HibernateTransaction.closeSessionFactory();
  }

  private static <T> Stream<T> generateStreamForClass(Class<T> clazz, int total){
    List<T> recs = new ArrayList<>();
    for(int i=0; i<total; i++){
      try {
        recs.add(clazz.newInstance());
      } catch (Exception e){
        throw new RuntimeException(e);
      }
    }

    return recs.stream();
  }
}
