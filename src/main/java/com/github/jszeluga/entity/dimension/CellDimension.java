package com.github.jszeluga.entity.dimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CELL_DIM")
public class CellDimension {

  @Id
  @Column(name = "cell_key")
  private long cellKey;


  public long getCellKey() {
    return cellKey;
  }

  public void setCellKey(final long cellKey) {
    this.cellKey = cellKey;
  }
}
