package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.CellDimension;
import com.github.jszeluga.util.HibernateTransaction;

import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateTransactionTest  {

  @Test
  public void testHibernateTransaction() throws Exception {
    HibernateTransaction.doWithSession(session -> {
      CellDimension cell1 = new CellDimension();
      cell1.setCellKey(9876543210L);
      session.save(cell1);
    });

    HibernateTransaction.doWithSession(session -> {
      CellDimension cell1 = session.get(CellDimension.class, 9876543210L);

      assertNotNull(cell1);
      assertEquals(9876543210L, cell1.getCellKey());


      Query<CellDimension> query = session.createQuery("from CellDimension where cell_key = ?0",
          CellDimension.class);

      query.setParameter(0, 9876543210L, LongType.INSTANCE);
      List<CellDimension> resultList = query.getResultList();

      assertNotNull(resultList);
      assertEquals(1, resultList.size());
      CellDimension cell2 = resultList.get(0);
      assertEquals(9876543210L, cell2.getCellKey());

    });
  }

  @After
  public void closeSessionFactory(){
    HibernateTransaction.closeSessionFactory();
  }
}
