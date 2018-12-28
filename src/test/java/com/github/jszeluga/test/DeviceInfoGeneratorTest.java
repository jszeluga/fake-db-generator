package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.generators.device.DeviceInfoGenerator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DeviceInfoGeneratorTest {

    @Test
    public void testGenerator() throws Exception {
        DeviceInfoGenerator generator = new DeviceInfoGenerator();
        DeviceDimension device = new DeviceDimension();

        assertNull(device.getVendor());
        assertNull(device.getModel());

        generator.initialize();
        generator.accept(device);

        assertNotNull(device.getVendor());
        assertNotNull(device.getModel());
    }
}
