package com.github.jszeluga.entity.fact;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.entity.InsertEntity;
import com.github.jszeluga.generators.facts.lte.LteFactGenerator;

import java.sql.Timestamp;

@Generators(generators = {
        LteFactGenerator.class
})
public class LteFact implements InsertEntity {


    private long id;

    private Timestamp recordDate;

    private long dispositionKey;

    private long startCellKey;

    private long endCellKey;

    private long deviceKey;

    private long customerKey;

    private Double sinr;

    private Double rsrp;

    private boolean droppedCall;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Timestamp recordDate) {
        this.recordDate = recordDate;
    }

    public Double getSinr() {
        return sinr;
    }

    public void setSinr(Double sinr) {
        this.sinr = sinr;
    }

    public Double getRsrp() {
        return rsrp;
    }

    public void setRsrp(Double rsrp) {
        this.rsrp = rsrp;
    }

    public boolean isDroppedCall() {
        return droppedCall;
    }

    public void setDroppedCall(boolean droppedCall) {
        this.droppedCall = droppedCall;
    }

    public long getDispositionKey() {
        return dispositionKey;
    }

    public void setDispositionKey(long dispositionKey) {
        this.dispositionKey = dispositionKey;
    }

    public long getStartCellKey() {
        return startCellKey;
    }

    public void setStartCellKey(long startCellKey) {
        this.startCellKey = startCellKey;
    }

    public long getEndCellKey() {
        return endCellKey;
    }

    public void setEndCellKey(long endCellKey) {
        this.endCellKey = endCellKey;
    }

    public long getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(long deviceKey) {
        this.deviceKey = deviceKey;
    }

    public long getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(long customerKey) {
        this.customerKey = customerKey;
    }

    @Override
    public Object[] getInsertParams() {
        return new Object[]{customerKey,deviceKey,dispositionKey,recordDate,startCellKey,endCellKey,sinr,rsrp,droppedCall};
    }
}
