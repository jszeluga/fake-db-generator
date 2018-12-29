package com.github.jszeluga.entity.fact;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.generators.facts.lte.LteFactGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "LTE_F", indexes = {
        @Index(columnList = "customer_key", name = "idx$custkey")
})
@Generators(generators = {
        LteFactGenerator.class
})
public class LteFact {

    //TODO: Use actual entities and do the join properly

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "disposition_key")
    private long dispositionKey;

    @Column(name = "start_cell_key")
    private long startCellKey;

    @Column(name = "end_cell_key")
    private long endCellKey;

    @Column(name = "device_key")
    private long deviceKey;

    @Column(name = "customer_key")
    private long customerKey;

    @Column(name = "sinr")
    private Double sinr;

    @Column(name = "rsrp")
    private Double rsrp;

    @Column(name = "dropped_call")
    private boolean droppedCall;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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
}
