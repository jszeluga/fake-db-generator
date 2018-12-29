package com.github.jszeluga.generators.dimensions.cell;

import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.generators.AbstractGenerator;

public class CellGenerator extends AbstractGenerator<CellDimension> {

    private int cellNum = 1;
    private int sectorNum = 1;
    private int carrierNum = 0;

    @Override
    public void accept(CellDimension cellDimension) {
        carrierNum++;
        if(carrierNum == 4){
            sectorNum++;
            carrierNum = 1;
        }

        if(sectorNum == 4){
            cellNum++;
            sectorNum = 1;
            carrierNum = 1;
        }

        cellDimension.setCarrier(carrierNum);
        cellDimension.setSector(sectorNum);

        cellDimension.setName("CELL_" + cellNum + "_" + sectorNum + "_" + carrierNum);
    }
}
