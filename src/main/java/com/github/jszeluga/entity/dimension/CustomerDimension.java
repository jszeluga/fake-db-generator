package com.github.jszeluga.entity.dimension;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.entity.InsertEntity;
import com.github.jszeluga.generators.dimensions.customer.CustomerKeyMdnGenerator;
import com.github.jszeluga.generators.dimensions.customer.NameGenerator;
import com.github.jszeluga.generators.dimensions.customer.PrepaidGenerator;
import com.github.jszeluga.generators.dimensions.customer.StateRegionGenerator;


@Generators(generators = {
        CustomerKeyMdnGenerator.class,
        NameGenerator.class,
        StateRegionGenerator.class,
        PrepaidGenerator.class
})
public class CustomerDimension implements InsertEntity {

    private long customerKey;

    private String name;

    private String mdn;

    private String region;

    private String state;

    private boolean prePaid;

    public long getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(long customerKey) {
        this.customerKey = customerKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isPrePaid() {
        return prePaid;
    }

    public void setPrePaid(boolean prePaid) {
        this.prePaid = prePaid;
    }

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }

    @Override
    public Object[] getInsertParams() {
        return new Object[]{customerKey,mdn,name,region,state,prePaid};
    }
}
