package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.customer.NameGenerator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NameGeneratorTest extends AbstractTest {

    @Test
    public void testNameGenerator() throws Exception {
        CustomerDimension customer = new CustomerDimension();
        NameGenerator generator = new NameGenerator();

        assertNull(customer.getName());
        generator.accept(customer);
        assertNotNull(customer.getName());
    }
}
