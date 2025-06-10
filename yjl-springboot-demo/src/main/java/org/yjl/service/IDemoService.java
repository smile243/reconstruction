package org.yjl.service;

import org.yjl.domain.entity.Demo;

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

    Demo cachePut(Demo demo);

    void cacheRemove(Long id);
}
