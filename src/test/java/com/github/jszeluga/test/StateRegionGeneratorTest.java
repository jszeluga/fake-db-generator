package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.customer.StateRegionGenerator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StateRegionGeneratorTest {

    @Test
    public void testStateRegionGenerator() throws Exception {
        StateRegionGenerator srGen = new StateRegionGenerator();
        srGen.initialize();

        CustomerDimension cust = new CustomerDimension();
        assertNull(cust.getState());
        assertNull(cust.getRegion());

        srGen.accept(cust);

        assertNotNull(cust.getState());
        assertNotNull(cust.getRegion());
    }
}
