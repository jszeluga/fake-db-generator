package com.github.jszeluga.util;

import com.github.jszeluga.entity.InsertEntity;
import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.entity.dimension.DispositionDimension;
import com.github.jszeluga.entity.fact.LteFact;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceUtil {

    private static SQLiteDataSource dataSource = null;
    private static Map<Class<? extends InsertEntity>, String> insertStatementMap = new HashMap<>();

    static {
        insertStatementMap.put(CellDimension.class, "insert into cell_dim(carrier,name,sector) values (?,?,?)");
        insertStatementMap.put(CustomerDimension.class, "insert into customer_dim(customer_key,mdn,name,region,state,pre_paid) values (?,?,?,?,?,?)");
        insertStatementMap.put(DeviceDimension.class, "insert into device_dim(vendor,model,marketing_name,device_os,device_os_version,volte) values (?,?,?,?,?,?)");
        insertStatementMap.put(DispositionDimension.class, "insert into disposition_dim(sip_code,code_name,outcome,description,failure_due_to_client,failure_due_to_server) values (?,?,?,?,?,?)");
        insertStatementMap.put(LteFact.class, "insert into lte_f(customer_key,device_key,disposition_key,record_date,start_cell_key,end_cell_key,sinr,rsrp,dropped_call) values (?,?,?,?,?,?,?,?,?)");
    }

    //TODO: use lambda with Connection and statement to do inserts

    public static DataSource getDataSource(){
        if(dataSource==null){
            dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:phoneCompany.db");
            dataSource.setJournalMode("MEMORY");
        }

        return dataSource;
    }

    public static String getInsertStatement(Class<? extends InsertEntity> clazz){
        return insertStatementMap.get(clazz);
    }
}
