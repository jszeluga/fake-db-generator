package com.github.jszeluga;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.generators.AbstractGenerator;
import com.github.jszeluga.generators.Generator;
import com.github.jszeluga.util.HibernateTransaction;
import com.github.jszeluga.util.ReflectionUtil;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MainApp {

  private static Map<Class<? extends Generator>, Generator> generatorMap = new HashMap<>();

  public static void main(String[] args){

    HibernateTransaction.openSessionFactory();
    initializeGenerators();
    generateAndInsertRecords(CustomerDimension.class, 100);
    generateAndInsertRecords(DeviceDimension.class, 100);

    HibernateTransaction.closeSessionFactory();
  }

  @SuppressWarnings("unchecked")
  private static <T> void generateAndInsertRecords(Class<T> clazz, int total){
    List<T> recs = new ArrayList<>();
    try {
      for (int i = 0; i < total; i++) {
        recs.add(clazz.newInstance());
      }

      Generators generators = clazz.getDeclaredAnnotation(Generators.class);


      //probably too fancy
      Function<Supplier<Stream<T>>, Stream<T>> flow = Supplier::get;

      for (Class<? extends Generator> genClass : generators.generators()) {
        flow = flow.andThen(stream->stream.peek(generatorMap.get(genClass)));
      }

      flow.apply(recs::stream).forEach(rec->HibernateTransaction.doWithSession(session->session.save(rec)));


    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }

  private static void initializeGenerators(){
    Reflections reflections = ReflectionUtil.REFLECTIONS;
    Set<Class<? extends AbstractGenerator>> generators = reflections.getSubTypesOf(AbstractGenerator.class);
    generators.forEach(gen -> {
      try {
        AbstractGenerator abstractGenerator = gen.newInstance();
        abstractGenerator.initialize();
        generatorMap.put(gen, abstractGenerator);
      } catch (Exception e){
        throw new RuntimeException(e);
      }
    });
  }
}
