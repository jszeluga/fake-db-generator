package com.github.jszeluga.generators.dimensions.customer;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.generators.AbstractGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerKeyMdnGenerator extends AbstractGenerator<CustomerDimension> {

    private List<Long> customerKeys = new ArrayList<>();

    @Override
    public void initialize() throws Exception {
        //want to initialize with numbers from
        // 555-555-0000 to 555-555-9999
        long start = 5555550000L;

        while(start <= 5555559999L){
            customerKeys.add(start++);
        }


    }

    @Override
    public void accept(CustomerDimension customerDimension) {
        if(customerDimension != null){
            if(!customerKeys.isEmpty()) {
                int index = ThreadLocalRandom.current().nextInt(0, customerKeys.size());
                Long key = customerKeys.get(index);
                customerKeys.remove(index); //remove phone number from the list to avoid duplicates

                //MDN has format of xxx-xxx-xxxx
                String mdn = Long.toString(key);
                mdn = mdn.replaceAll("([\\d]{3})([\\d]{3})([\\d]{4})", "$1-$2-$3");

                customerDimension.setCustomerKey(key);
                customerDimension.setMdn(mdn);
            } else {
                throw new RuntimeException("Cannot make any more customers");
            }
        }
    }
}
