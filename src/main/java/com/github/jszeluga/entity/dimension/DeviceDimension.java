package com.github.jszeluga.entity.dimension;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.entity.InsertEntity;
import com.github.jszeluga.generators.dimensions.device.DeviceInfoGenerator;


@Generators(generators = {
        DeviceInfoGenerator.class
})
public class DeviceDimension implements InsertEntity {

    private long deviceKey;

    private String vendor;

    private String model;

    private String marketingName;

    private String deviceOs;

    private String deviceOsVersion;

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

    @Override
    public Object[] getInsertParams() {
        return new Object[]{vendor,model,marketingName,deviceOs,deviceOsVersion,volte};
    }
}
