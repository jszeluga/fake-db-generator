package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.DispositionDimension;
import com.github.jszeluga.generators.disposition.DispositionGenerator;
import com.github.jszeluga.util.HibernateTransaction;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateTransaction.class)
public class DispositionGeneratorTest {

    @Test
    public void testGenerator() throws Exception {
        List<DispositionDimension> recs = new ArrayList<>();

        PowerMockito.mockStatic(HibernateTransaction.class);
        Session mockSession = Mockito.mock(Session.class);

        PowerMockito.doAnswer(ctx->{
            DispositionDimension rec = ctx.getArgument(0);
            recs.add(rec);
            return null;
        }).when(mockSession).save(any(DispositionDimension.class));


        PowerMockito.doAnswer(ctx->{
            Consumer<Session> function = ctx.getArgument(0);
            function.accept(mockSession);
            return null;
        }).when(HibernateTransaction.class, "doWithSession", any(Consumer.class));

        DispositionGenerator generator = new DispositionGenerator();
        generator.initialize();

        assertFalse(recs.isEmpty());
        assertEquals(71, recs.size());

    }

}
