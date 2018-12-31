package com.github.jszeluga.generators.facts.lte;

import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.entity.dimension.DispositionDimension;
import com.github.jszeluga.entity.fact.LteFact;
import com.github.jszeluga.generators.AbstractGenerator;
import com.github.jszeluga.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());


        cells = queryRunner.query("select * from cell_dim", rs->{
            List<CellDimension> recs = new ArrayList<>();
            while(rs.next()){
                CellDimension cell = new CellDimension();
                cell.setName(rs.getString("name"));
                cell.setSector(rs.getInt("sector"));
                cell.setCarrier(rs.getInt("carrier"));
                cell.setCellKey(rs.getLong("cell_key"));

                recs.add(cell);
            }

            return recs;
        });

        List<DispositionDimension> dispositions = queryRunner.query("select * from disposition_dim", rs->{
            List<DispositionDimension> recs= new ArrayList<>();
            while(rs.next()){
                DispositionDimension disp = new DispositionDimension();
                disp.setDispositionKey(rs.getLong("disposition_key"));
                disp.setSipCode(rs.getInt("sip_code"));
                disp.setCodeName(rs.getString("code_name"));
                disp.setOutcome(rs.getString("outcome"));
                disp.setDispositionKey(rs.getShort("description"));
                disp.setFailureDueToClient(rs.getBoolean("failure_due_to_client"));
                disp.setFailureDueToServer(rs.getBoolean("failure_due_to_server"));

                recs.add(disp);
            }

            return recs;
        });
        customers = queryRunner.query("select * from customer_dim", rs->{
            List<CustomerDimension> recs= new ArrayList<>();
            while(rs.next()){
                CustomerDimension cust = new CustomerDimension();
                cust.setCustomerKey(rs.getLong("customer_key"));
                cust.setMdn(rs.getString("mdn"));
                cust.setName(rs.getString("name"));
                cust.setRegion(rs.getString("region"));
                cust.setState(rs.getString("state"));
                cust.setPrePaid(rs.getBoolean("pre_paid"));

                recs.add(cust);
            }

            return recs;
        });

        devices = queryRunner.query("select * from device_dim", rs->{
            List<DeviceDimension> recs = new ArrayList<>();
            while(rs.next()){
                DeviceDimension device = new DeviceDimension();
                device.setDeviceKey(rs.getLong("device_key"));
                device.setVendor(rs.getString("vendor"));
                device.setModel(rs.getString("model"));
                device.setMarketingName(rs.getString("marketing_name"));
                device.setDeviceOs(rs.getString("device_os"));
                device.setDeviceOsVersion(rs.getString("device_os_version"));
                device.setVolte(rs.getBoolean("volte"));

                recs.add(device);
            }
            return recs;
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


        //Generate dates between now and a week from now UTC
        ZonedDateTime startDateTime = ZonedDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime endDateTime = startDateTime.plusDays(7);

        long timestamp = random.nextLong(startDateTime.toInstant().toEpochMilli(), endDateTime.toInstant().toEpochMilli());
        lteFact.setRecordDate(new Timestamp(timestamp));

        //Randomly decide if this is a dropped call
        //It doesn't take into account the disposition outcome
        boolean droppedCall = random.nextBoolean();
        lteFact.setDroppedCall(droppedCall);
    }
}
