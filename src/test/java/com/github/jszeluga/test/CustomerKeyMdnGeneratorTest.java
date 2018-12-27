package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.customer.CustomerKeyMdnGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerKeyMdnGeneratorTest {

    @Test
    public void testCustomerKeyMdnGenerator() throws Exception {
        CustomerKeyMdnGenerator gen = new CustomerKeyMdnGenerator();
        gen.initialize();

        CustomerDimension cust = new CustomerDimension();
        assertEquals(0L, cust.getCustomerKey());
        assertNull(cust.getMdn());

        gen.accept(cust);

        assertNotEquals(0L, cust.getCustomerKey());
        assertNotNull(cust.getMdn());

        String[] split = cust.getMdn().split("-");
        assertEquals(3, split.length);
        long key = Long.parseLong(split[0] + split[1] + split[2]);
        assertEquals(key, cust.getCustomerKey());
    }
}
