package org.yjl.service;

public interface IDemoService {


    default void testLock() throws InterruptedException {

    }

    default void insertBatch() {

    }

    default void mybatisTest() {

    }

    default void transactionCallBack(){}

    void multiDatasource();

    default void transaction(){}

    void transactionEvent();

    void dataSourcePool();
}
