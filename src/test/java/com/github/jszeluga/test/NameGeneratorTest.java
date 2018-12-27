package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.customer.NameGenerator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NameGeneratorTest {

    @Test
    public void testNameGenerator() throws Exception {
        CustomerDimension customer = new CustomerDimension();
        NameGenerator generator = new NameGenerator();

        assertNull(customer.getName());
        generator.initialize();
        generator.accept(customer);
        assertNotNull(customer.getName());
    }
}
