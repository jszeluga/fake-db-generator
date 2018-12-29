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
import org.apache.commons.lang3.math.NumberUtils;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MainApp {

    private static Map<Class<? extends Generator>, Generator> generatorMap = new HashMap<>();

    public static void main(String[] args){

        HibernateTransaction.openSessionFactory();
        initializeDimensionGenerators();

        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter size to make CUSTOMER_DIM (default 300): ");
        String custDimSize = scanner.nextLine();
        int custDimNum = 300;
        if(NumberUtils.isDigits(custDimSize)){
            custDimNum = Integer.parseInt(custDimSize);
        }

        int deviceNum = 500;
        System.out.print("Enter size to make DEVICE_DIM (default 500): ");
        String deviceDimSize = scanner.nextLine();
        if(NumberUtils.isDigits(deviceDimSize)){
            deviceNum = Integer.parseInt(deviceDimSize);
        }

        int cellDimNum = 200;
        System.out.print("Enter size to make CELL_DIM (default 200): ");
        String cellDimSize = scanner.nextLine();
        if(NumberUtils.isDigits(cellDimSize)){
            cellDimNum = Integer.parseInt(cellDimSize);
        }

        int lteFactNum = 10000;
        System.out.print("Enter size to make LTE_F (default 10000): ");
        String lteFactSize = scanner.nextLine();
        if(NumberUtils.isDigits(lteFactSize)){
            lteFactNum = Integer.parseInt(lteFactSize);
        }


        generateAndInsertRecords(CustomerDimension.class, custDimNum);
        generateAndInsertRecords(DeviceDimension.class, deviceNum);
        generateAndInsertRecords(CellDimension.class, cellDimNum);

        //special case
        //all records are loaded in the initialize method
        generateAndInsertRecords(DispositionDimension.class, 0);

        initializeFactGenerators();
        generateAndInsertRecords(LteFact.class, lteFactNum);

        HibernateTransaction.closeSessionFactory();
        scanner.close();
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
