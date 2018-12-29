package com.github.jszeluga.entity.dimension;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.generators.dimensions.device.DeviceInfoGenerator;

import javax.persistence.*;

@Entity
@Table(name = "DEVICE_DIM")
@Generators(generators = {
        DeviceInfoGenerator.class
})
public class DeviceDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_key")
    private long deviceKey;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "model")
    private String model;

    @Column(name = "marketing_name")
    private String marketingName;

    @Column(name = "device_os")
    private String deviceOs;

    @Column(name = "device_os_version")
    private String deviceOsVersion;

    @Column(name = "volte")
    private boolean volte;

    public long getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(long deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceOsVersion() {
        return deviceOsVersion;
    }

    public void setDeviceOsVersion(String deviceOsVersion) {
        this.deviceOsVersion = deviceOsVersion;
    }

    public boolean isVolte() {
        return volte;
    }

    public void setVolte(boolean volte) {
        this.volte = volte;
    }
}
