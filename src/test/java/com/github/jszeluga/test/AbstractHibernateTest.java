package com.github.jszeluga.test;

import com.github.jszeluga.util.HibernateTransaction;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractHibernateTest {

    @Before
    public void openHibernateSessionFactory(){
        HibernateTransaction.openSessionFactory();
    }

    @After
    public void closeHibernateSessionFactory(){
        HibernateTransaction.closeSessionFactory();
    }
}
