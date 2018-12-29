package com.github.jszeluga;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.entity.dimension.DispositionDimension;
import com.github.jszeluga.entity.fact.LteFact;
import com.github.jszeluga.generators.AbstractGenerator;
import com.github.jszeluga.generators.Generator;
import com.github.jszeluga.util.HibernateTransaction;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MainApp {

    private static Map<Class<? extends Generator>, Generator> generatorMap = new HashMap<>();

    public static void main(String[] args){

        //TODO: add user input processing for custom sizes

        HibernateTransaction.openSessionFactory();
        initializeDimensionGenerators();
        generateAndInsertRecords(CustomerDimension.class, 300);
        generateAndInsertRecords(DeviceDimension.class, 500);
        generateAndInsertRecords(CellDimension.class, 200);

        //special case
        //all records are loaded in the initialize method
        generateAndInsertRecords(DispositionDimension.class, 0);

        initializeFactGenerators();
        generateAndInsertRecords(LteFact.class, 10000);

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
            Function<Supplier<Stream<T>>, Stream<T>> tempFlow = Supplier::get;

            for (Class<? extends Generator> genClass : generators.generators()) {
                tempFlow = tempFlow.andThen(stream->stream.peek(generatorMap.get(genClass)));
            }

            final Function<Supplier<Stream<T>>, Stream<T>> flow = tempFlow;
            HibernateTransaction.doWithSession(session->{
                flow.apply(recs::stream).forEach(session::save);
            });

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static void initializeDimensionGenerators(){
        Reflections reflections = new Reflections("com.github.jszeluga.generators.dimensions");
        initializeGenerators(reflections);
    }

    private static void initializeFactGenerators() {
        Reflections reflections = new Reflections("com.github.jszeluga.generators.facts");
        initializeGenerators(reflections);
    }

    private static void initializeGenerators(Reflections reflections){
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
