package com.github.jszeluga.entity;

import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.entity.dimension.DispositionDimension;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "LTE_F", indexes = {
        @Index(columnList = "customer_key", name = "idx$custkey")
})
public class LteFact {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "date")
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "disposition_key")
    private DispositionDimension disposition;

    @ManyToOne
    @JoinColumn(name = "start_cell_key", referencedColumnName = "cell_key")
    private CellDimension startCell;

    @ManyToOne
    @JoinColumn(name = "end_cell_key", referencedColumnName = "cell_key")
    private CellDimension endCell;

    @ManyToOne
    @JoinColumn(name = "device_key")
    private DeviceDimension device;

    @ManyToOne
    @JoinColumn(name = "customer_key")
    private CustomerDimension customer;

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

    public CellDimension getStartCell() {
        return startCell;
    }

    public void setStartCell(CellDimension startCell) {
        this.startCell = startCell;
    }

    public CellDimension getEndCell() {
        return endCell;
    }

    public void setEndCell(CellDimension endCell) {
        this.endCell = endCell;
    }

    public DeviceDimension getDevice() {
        return device;
    }

    public void setDevice(DeviceDimension device) {
        this.device = device;
    }

    public CustomerDimension getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDimension customer) {
        this.customer = customer;
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

    public DispositionDimension getDisposition() {
        return disposition;
    }

    public void setDisposition(DispositionDimension disposition) {
        this.disposition = disposition;
    }

    public boolean isDroppedCall() {
        return droppedCall;
    }

    public void setDroppedCall(boolean droppedCall) {
        this.droppedCall = droppedCall;
    }
}
