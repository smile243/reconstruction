package org.yjl.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.yjl.domain.entity.Demo;
import org.yjl.domain.entity.Student;
import org.yjl.mapper.DemoMapper;
import org.yjl.mapper.StudentMapper;
import org.yjl.service.IDemoService;
import org.yjl.utils.SpringUtils;
import org.yjl.utils.TransactionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yjl
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DemoServiceImpl implements IDemoService {
    private final TransactionUtils transactionUtils;
    private final DemoMapper mapper;
    private final StudentMapper studentMapper;
    private final Executor futureExecutor;

    private final Lock lock = new ReentrantLock();
    private int value;
    //是否可以提交
    public static volatile boolean IS_OK = true;

    @Override
    public void testLock() throws InterruptedException {
        Set<Integer> set = new ConcurrentHashSet<>();
        value = 0;
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            Thread A = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        int t = getNext();
                        System.out.print(t + "\t");
                        set.add(t);
                        count.getAndIncrement();
                    }
                }
            });
            A.start();
        }
//        while(count.get()<=24){
//        }
//        System.out.println();
//        if(set.size()<25){
//            System.out.println("重复了");
//        }
    }

    public int getNext() {
        lock.lock();
        try {
            value = value + 1;
            //先返回后释放锁
            return value;
        } finally {
            lock.unlock();
        }
        //此处return会有问题
    }

    @Override
    public void insertBatch() {
        //子线程等待主线程通知
        CountDownLatch mainMonitor = new CountDownLatch(1);
        int threadCount = 5;
        CountDownLatch childMonitor = new CountDownLatch(threadCount);
        //子线程运行结果
        List<Boolean> childResponse = new ArrayList();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executor.execute(() -> {
                //开启事务
                TransactionStatus transactionStatus = transactionUtils.begin();
                try {
                    studentMapper.insertBatch(buildStudentList());
                    if (finalI == 4) {
                        throw new Exception("出现异常");
                    }
                    childResponse.add(Boolean.TRUE);
                    childMonitor.countDown();
                    log.info("线程{}正常执行完成,等待其他线程执行结束,判断是否需要回滚", Thread.currentThread().getName());
                    mainMonitor.await();
                    if (IS_OK) {
                        log.info("所有线程都正常完成,线程事务提交", Thread.currentThread().getName());
                        transactionUtils.commit(transactionStatus);
                    } else {
                        log.info("有线程出现异常,线程小事务回滚", Thread.currentThread().getName());
                        transactionUtils.rollback(transactionStatus);
                    }
                } catch (Exception e) {
                    childResponse.add(Boolean.FALSE);
                    childMonitor.countDown();
                    log.error("线程{}发生了异常,开始进行事务回滚", Thread.currentThread().getName());
                }
            });
        }
        try {
            //主线程等待所有子线程执行response
            childMonitor.await();
            for (Boolean resp : childResponse) {
                if (!resp) {
                    //如果有一个子线程执行失败了，则改变mainResult，让所有子线程回滚
                    log.info("{}:IS_OK的值被修改为false", Thread.currentThread().getName());
                    IS_OK = false;
                    break;
                }
            }
            //主线程获取结果成功，让子线程开始根据主线程的结果执行（提交或回滚）
            mainMonitor.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Student> buildStudentList() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Student student = new Student();
            student.setName("yjl" + i);
            student.setHome("big house");
            list.add(student);
        }
        return list;
    }

    /**
     * @Description: Transactional必须开启
     * SpringFramework / SpringBoot 整合 MyBatis 后，
     * Service方法中没有开启事务时每次调用Mapper查询数据时,底层都会创建一个全新的 SqlSession 去查数据库
     * @Date: 2023/2/21
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mybatisTest() {
        Demo test = mapper.selectById(4);
        System.out.println(test);
        test.setAge(30);
        System.out.println(test);
        Demo test2 = mapper.selectById(4);
        System.out.println(test2);
        System.out.println(test == test2);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactionCallBack() {
        mapper.selectList();
        //是回调的，但是整个响应还是会等待该方法执行结束，但并不影响响应的结果
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_COMMITTED) {
                    log.info("事务成功执行完成后回调");
                }
                if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                    log.info("事务异常回滚执行完成后回调");
                }
                int a = 1 / 0;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("事务只要提交就回调");
            }
        });
    }

    /***
     *MultiDataSourceTransaction和Propagation.REQUIRES_NEW都可以解决解决查询的多数据源查询,MultiDataSourceTransaction增删改查都能解决,Propagation.REQUIRES_NEW只能查询
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void multiDatasource() {
        mapper.update(Wrappers.<Demo>lambdaUpdate().set(Demo::getAge, 18));
        int a = 1 / 0;
    }

    //    public void test2(){
//        List<Demo> list=new ArrayList();
//        Demo test=new Demo();
//        test.setAge(19);
//        Demo test2=new Demo();
//        test2.setAge(19);
//        list.add(test2);
//        list.add(test);
//        service.saveRecord(list);
//        int num=1/0;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transaction() {
        mapper.update(Wrappers.<Demo>lambdaUpdate().set(Demo::getAge, 18));
        //int a = 1 / 0;
        mapper.delete(Wrappers.query());
    }

    /**
     * 有事务并且事务成功(默认)的方法publishEvent后@TransactionalEventListener才会起作用
     * The valid phases are BEFORE_COMMIT, AFTER_COMMIT (default)成功, AFTER_ROLLBACK,
     * as well as AFTER_COMPLETION which aggregates the transaction completion (be it a commit or a rollback)不管成功与否
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactionEvent() {
        Demo demo = new Demo();
        SpringUtils.getApplicationContext().publishEvent(demo);
        int a = 1 / 0;
        mapper.selectList();
    }

    /**
     * spring默认会引入hikari，什么都不配置的话，连接池最大为10，超过的请求会等待，超过30s没获取到连接就会提示cannot open connection
     */
    @Override
    public void dataSourcePool() {
            mapper.update(Wrappers.<Demo>lambdaUpdate().set(Demo::getAge, 18));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
