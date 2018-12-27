package com.github.jszeluga.generators.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.Generator;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.GZIPInputStream;

public class StateRegionGenerator implements Generator<CustomerDimension> {

    private static CsvMapper csvMapper = new CsvMapper();
    private static List<StateRegion> stateRegionList;

    @Override
    public void initialize() throws Exception {
        InputStream statesRegionsStream = this.getClass().getClassLoader().getResourceAsStream("states_regions.csv.gz");
        Objects.requireNonNull(statesRegionsStream);

        try (GZIPInputStream srGzip = new GZIPInputStream(statesRegionsStream)){
            CsvSchema csvSchema = csvMapper.schemaFor(StateRegion.class).withHeader();
            ObjectReader objectReader = csvMapper.readerFor(StateRegion.class).with(csvSchema);
            stateRegionList = objectReader.<StateRegion>readValues(srGzip).readAll();
        }

    }

    @Override
    public void accept(CustomerDimension customerDimension) {

        if(customerDimension!=null){
            int index = ThreadLocalRandom.current().nextInt(0, stateRegionList.size());
            StateRegion stateRegion = stateRegionList.get(index);
            customerDimension.setState(stateRegion.getState());
            customerDimension.setRegion(stateRegion.getRegion());
        }

    }

    @JsonPropertyOrder(value = {"State", "Region"})
    private static class StateRegion {
        @JsonProperty(value = "State")
        private String state;

        @JsonProperty(value = "Region")
        private String region;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }
}
