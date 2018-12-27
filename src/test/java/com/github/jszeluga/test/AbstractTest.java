package com.github.jszeluga.test;

import com.github.jszeluga.util.HibernateTransaction;
import org.junit.After;

public abstract class AbstractTest {

    @After
    public void closeHibernateSessionFactory(){
        HibernateTransaction.closeSessionFactory();
    }
}
