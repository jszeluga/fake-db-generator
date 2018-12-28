package com.github.jszeluga.generators.disposition;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.jszeluga.entity.dimension.DispositionDimension;
import com.github.jszeluga.generators.AbstractGenerator;
import com.github.jszeluga.util.HibernateTransaction;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class DispositionGenerator extends AbstractGenerator<DispositionDimension> {

    private static CsvMapper csvMapper = new CsvMapper();

    static {
        csvMapper.addMixIn(DispositionDimension.class, DispositionMixIn.class);
    }

    @Override
    public void initialize() throws Exception {
        InputStream sipCodesStream = this.getClass().getClassLoader().getResourceAsStream("sip_codes.csv.gz");
        Objects.requireNonNull(sipCodesStream);
         try (GZIPInputStream scGzip = new GZIPInputStream(sipCodesStream)){
             CsvSchema csvSchema = csvMapper.schemaFor(DispositionDimension.class).withHeader();
             ObjectReader objectReader = csvMapper.readerFor(DispositionDimension.class).with(csvSchema);
             List<DispositionDimension> dispostionList = objectReader.<DispositionDimension>readValues(scGzip).readAll();

             //Load the entire disposition dimension into the db
             dispostionList.forEach(rec->HibernateTransaction.doWithSession(session->session.save(rec)));
         }

    }

    @Override
    public void accept(DispositionDimension dispositionDimension) {
        //do nothing for this case
        //we want to load the entire csv into the db
    }
}
