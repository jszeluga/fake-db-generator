package com.github.jszeluga.entity.dimension;

import javax.persistence.*;

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

    public void setCellKey(long cellKey) {
        this.cellKey = cellKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getCarrier() {
        return carrier;
    }

    public void setCarrier(int carrier) {
        this.carrier = carrier;
    }
}
