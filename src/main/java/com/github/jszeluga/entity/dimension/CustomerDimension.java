package com.github.jszeluga.entity.dimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_DIM")
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

  public void setCustomerKey(final long customerKey) {
    this.customerKey = customerKey;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(final String region) {
    this.region = region;
  }

  public String getState() {
    return state;
  }

  public void setState(final String state) {
    this.state = state;
  }

  public boolean isPrePaid() {
    return prePaid;
  }

  public void setPrePaid(final boolean prePaid) {
    this.prePaid = prePaid;
  }

  public String getMdn() {
    return mdn;
  }

  public void setMdn(final String mdn) {
    this.mdn = mdn;
  }
}
