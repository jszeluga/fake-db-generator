package com.github.jszeluga.generators.dimensions.customer;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class PrepaidGenerator extends AbstractGenerator<CustomerDimension> {

    @Override
    public void accept(CustomerDimension customerDimension) {
        if(customerDimension != null){
            boolean isPrepaid = ThreadLocalRandom.current().nextBoolean();
            customerDimension.setPrePaid(isPrepaid);
        }
    }
}
