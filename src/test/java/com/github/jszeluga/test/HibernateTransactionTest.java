package com.github.jszeluga.test;

import com.github.jszeluga.entity.dimension.CustomerDimension;
import com.github.jszeluga.util.HibernateTransaction;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateTransactionTest extends AbstractTest {

  @Test
  public void testHibernateTransaction() throws Exception {
    HibernateTransaction.doWithSession(session -> {
      CustomerDimension customer = new CustomerDimension();
      customer.setCustomerKey(5551234567L);
      session.save(customer);
    });

    HibernateTransaction.doWithSession(session -> {
      CustomerDimension customer1 = session.get(CustomerDimension.class, 5551234567L);

      assertNotNull(customer1);
      assertEquals(5551234567L, customer1.getCustomerKey());


      Query<CustomerDimension> query = session.createQuery("from CustomerDimension where customer_key = ?0",
          CustomerDimension.class);

      query.setParameter(0, 5551234567L, LongType.INSTANCE);
      List<CustomerDimension> resultList = query.getResultList();

      assertNotNull(resultList);
      assertEquals(1, resultList.size());
      CustomerDimension customer2 = resultList.get(0);
      assertEquals(5551234567L, customer2.getCustomerKey());

    });
  }
}
