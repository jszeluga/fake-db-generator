package com.github.jszeluga.entity.dimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CELL_DIM")
public class CellDimension {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "cell_key")
  private long cellKey;

  @Column(name = "name")
  private String name;

  @Column(name = "sector")
  private int sector;

  @Column(name = "carrier")
  private int carrier;


  public long getCellKey() {
    return cellKey;
  }

  public void setCellKey(final long cellKey) {
    this.cellKey = cellKey;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public int getSector() {
    return sector;
  }

  public void setSector(final int sector) {
    this.sector = sector;
  }

  public int getCarrier() {
    return carrier;
  }

  public void setCarrier(final int carrier) {
    this.carrier = carrier;
  }
}
