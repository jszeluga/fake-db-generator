package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.DispositionDimension;
import com.github.jszeluga.generators.dimensions.disposition.DispositionGenerator;
import com.github.jszeluga.util.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DataSourceUtil.class)
public class DispositionGeneratorTest {

    @Test
    public void testGenerator() throws Exception {
        List<DispositionDimension> recs = new ArrayList<>();

        QueryRunner mockRunner = Mockito.mock(QueryRunner.class);
        Connection mockConnection = Mockito.mock(Connection.class);

        PowerMockito.spy(DataSourceUtil.class);

        PowerMockito.doAnswer(ctx->{
            Object[][] insertParams = ctx.getArgument(2);
            for(Object[] param : insertParams){
                DispositionDimension rec = new DispositionDimension();
                rec.setSipCode((int)param[0]);
                rec.setCodeName((String)param[1]);
                rec.setOutcome((String)param[2]);
                rec.setDescription((String)param[3]);
                rec.setFailureDueToClient((boolean)param[4]);
                rec.setFailureDueToServer((boolean)param[5]);
                recs.add(rec);
            }
            return null;
        }).when(mockRunner).batch(any(Connection.class), anyString(), any());

        PowerMockito.doAnswer(ctx->{
            BiFunction<QueryRunner, Connection, int[]> function = ctx.getArgument(0);
            function.apply(mockRunner, mockConnection);
            return null;
        }).when(DataSourceUtil.class, "doInTransaction", any());

        DispositionGenerator generator = new DispositionGenerator();
        generator.initialize();

        assertFalse(recs.isEmpty());
        assertEquals(71, recs.size());

    }

}
