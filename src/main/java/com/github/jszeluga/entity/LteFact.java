package com.github.jszeluga.entity;

import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.entity.dimension.DispositionDimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(final Timestamp date) {
    this.date = date;
  }

  public CellDimension getStartCell() {
    return startCell;
  }

  public void setStartCell(final CellDimension startCell) {
    this.startCell = startCell;
  }

  public CellDimension getEndCell() {
    return endCell;
  }

  public void setEndCell(final CellDimension endCell) {
    this.endCell = endCell;
  }

  public DeviceDimension getDevice() {
    return device;
  }

  public void setDevice(final DeviceDimension device) {
    this.device = device;
  }

  public CustomerDimension getCustomer() {
    return customer;
  }

  public void setCustomer(final CustomerDimension customer) {
    this.customer = customer;
  }

  public Double getSinr() {
    return sinr;
  }

  public void setSinr(final Double sinr) {
    this.sinr = sinr;
  }

  public Double getRsrp() {
    return rsrp;
  }

  public void setRsrp(final Double rsrp) {
    this.rsrp = rsrp;
  }

  public DispositionDimension getDisposition() {
    return disposition;
  }

  public void setDisposition(final DispositionDimension disposition) {
    this.disposition = disposition;
  }
}
