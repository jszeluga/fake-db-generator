package com.github.jszeluga.generators.customer;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.Generator;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.GZIPInputStream;

public class NameGenerator implements Generator<CustomerDimension> {

    private static List<String> firstNames;
    private static List<String> lastNames;

    @Override
    public void initialize() throws Exception {
        ClassLoader classLoader = NameGenerator.class.getClassLoader();
        InputStream firstNamesStream = classLoader.getResourceAsStream("firstnames.txt.gz");
        InputStream lastNamesStream = classLoader.getResourceAsStream("lastnames.txt.gz");

        Objects.requireNonNull(firstNamesStream);
        Objects.requireNonNull(lastNamesStream);

        try (GZIPInputStream fnGzip = new GZIPInputStream(firstNamesStream);
             GZIPInputStream lnGzip = new GZIPInputStream(lastNamesStream)) {

            firstNames = IOUtils.readLines(fnGzip, StandardCharsets.UTF_8);
            lastNames = IOUtils.readLines(lnGzip, StandardCharsets.UTF_8);
        }
    }

    @Override
    public void accept(CustomerDimension customerDimension) {
        if(customerDimension!=null){
            int fnIndex = ThreadLocalRandom.current().nextInt(0, firstNames.size());
            int lnIndex = ThreadLocalRandom.current().nextInt(0, lastNames.size());

            customerDimension.setName(firstNames.get(fnIndex) + " " + lastNames.get(lnIndex));
        }
    }
}
