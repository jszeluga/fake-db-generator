package com.github.jszeluga.generators.facts.lte;

import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.entity.dimension.DispositionDimension;
import com.github.jszeluga.entity.fact.LteFact;
import com.github.jszeluga.generators.AbstractGenerator;
import com.github.jszeluga.util.HibernateTransaction;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class LteFactGenerator extends AbstractGenerator<LteFact> {

    private List<CellDimension> cells;
    private List<DispositionDimension> dispSucc;
    private List<DispositionDimension> dispFail;
    private List<CustomerDimension> customers;
    private List<DeviceDimension> devices;

    private Map<CustomerDimension, DeviceDimension> customerDeviceMap = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public void initialize() throws Exception {

        cells = HibernateTransaction.doWithSession(session->{
            return session.createQuery("from CellDimension").getResultList();
        });

        List<DispositionDimension> dispositions = HibernateTransaction.doWithSession(session->{
            return session.createQuery("from DispositionDimension", DispositionDimension.class).getResultList();
        });

        customers = HibernateTransaction.doWithSession(session->{
            return session.createQuery("from CustomerDimension", CustomerDimension.class).getResultList();
        });

        devices = HibernateTransaction.doWithSession(session->{
            return session.createQuery("from DeviceDimension", DeviceDimension.class).getResultList();
        });


        Objects.requireNonNull(cells);
        Objects.requireNonNull(dispositions);
        Objects.requireNonNull(customers);
        Objects.requireNonNull(devices);

        dispSucc = dispositions.stream()
                .filter(disp -> "SUCCESS".equals(disp.getOutcome()))
                .collect(Collectors.toList());

        dispFail = dispositions.stream()
                .filter(disp->!"SUCCESS".equals(disp.getOutcome()))
                .collect(Collectors.toList());
    }

    @Override
    public void accept(LteFact lteFact) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int cell1Idx = random.nextInt(0, cells.size());
        lteFact.setStartCellKey(cells.get(cell1Idx).getCellKey());

        int cell2Idx = random.nextInt(0, cells.size());
        lteFact.setEndCellKey(cells.get(cell2Idx).getCellKey());

        //pick more successful dispositions
        int dispChance = random.nextInt(0, 10);

        //80% change of success
        if(dispChance < 8){
            int dispIdx = random.nextInt(0, dispSucc.size());
            lteFact.setDispositionKey(dispSucc.get(dispIdx).getDispositionKey());
        } else {
            int dispIdx = random.nextInt(0, dispFail.size());
            lteFact.setDispositionKey(dispFail.get(dispIdx).getDispositionKey());
        }

        int custIdx = random.nextInt(0, customers.size());
        CustomerDimension customer = customers.get(custIdx);
        lteFact.setCustomerKey(customer.getCustomerKey());

        //one device per customer
        DeviceDimension device = customerDeviceMap.computeIfAbsent(customer, key->{
            int deviceIdx = random.nextInt(0, devices.size());
            return devices.get(deviceIdx);
        });

        lteFact.setDeviceKey(device.getDeviceKey());

        double sinr = random.nextDouble(-20.0, 21.0);
        lteFact.setSinr(sinr);

        double rsrp = random.nextDouble(-100.0, -79.0);
        lteFact.setRsrp(rsrp);


        ZonedDateTime startDateTime = ZonedDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime endDateTime = startDateTime.plusDays(7);

        long timestamp = random.nextLong(startDateTime.toInstant().toEpochMilli(), endDateTime.toInstant().toEpochMilli());
        lteFact.setDate(new Timestamp(timestamp));

        boolean droppedCall = random.nextBoolean();
        lteFact.setDroppedCall(droppedCall);
    }
}
