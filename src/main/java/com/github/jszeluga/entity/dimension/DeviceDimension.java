package com.github.jszeluga.entity.dimension;

import javax.persistence.*;

@Entity
@Table(name = "DEVICE_DIM")
public class DeviceDimension {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "device_key")
  private long deviceKey;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "model")
  private String model;

  @Column(name = "year")
  private String year;

  @Column(name = "lte_device")
  private boolean lteDevice;

  @Column(name = "volte_device")
  private boolean volteDevice;

  public long getDeviceKey() {
    return deviceKey;
  }

  public void setDeviceKey(long deviceKey) {
    this.deviceKey = deviceKey;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public boolean isLteDevice() {
    return lteDevice;
  }

  public void setLteDevice(boolean lteDevice) {
    this.lteDevice = lteDevice;
  }

  public boolean isVolteDevice() {
    return volteDevice;
  }

  public void setVolteDevice(boolean volteDevice) {
    this.volteDevice = volteDevice;
  }
}
