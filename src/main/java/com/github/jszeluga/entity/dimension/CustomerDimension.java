package com.github.jszeluga.entity.dimension;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.generators.dimensions.customer.CustomerKeyMdnGenerator;
import com.github.jszeluga.generators.dimensions.customer.NameGenerator;
import com.github.jszeluga.generators.dimensions.customer.PrepaidGenerator;
import com.github.jszeluga.generators.dimensions.customer.StateRegionGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_DIM")
@Generators(generators = {
        CustomerKeyMdnGenerator.class,
        NameGenerator.class,
        StateRegionGenerator.class,
        PrepaidGenerator.class
})
public class CustomerDimension {

    @Id
    @Column(name = "customer_key")
    private long customerKey;

    @Column(name = "name")
    private String name;

    @Column(name = "mdn")
    private String mdn;

    @Column(name = "region")
    private String region;

    @Column(name = "state")
    private String state;

    @Column(name = "pre_paid")
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
}
