package com.github.jszeluga.entity.dimension;

import com.github.jszeluga.annotation.Generators;
import com.github.jszeluga.generators.dimensions.cell.CellGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CELL_DIM")
@Generators(generators = {
        CellGenerator.class
})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellDimension that = (CellDimension) o;
        return sector == that.sector &&
                carrier == that.carrier &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sector, carrier);
    }
}
