package real.stock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author yjl
 * @since 2024/5/30
 */
@Slf4j
public class KillService {
    /**
     * 库存
     */
    public static Inventory inventory;

    private static final String killSkuId="1";

    /**
     * 并发计数
     */
    Map<String, AtomicInteger> counter = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        inventory = new Inventory(2000);
        int concurrentNum = 800;
        CountDownLatch countDownLatch = new CountDownLatch(concurrentNum);
        KillService killService = new KillService();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(concurrentNum,
                concurrentNum,
                2,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("user-request-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < concurrentNum; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    /**
                     * countDownLatch模拟多个线程并发
                     */
                    countDownLatch.countDown();
                    countDownLatch.await();
                    long start = System.currentTimeMillis();
                    // 发送并发请求
                    R r = killService.commitRequest(killSkuId, RandomUtils.nextInt(1, 5));
                    long end = System.currentTimeMillis();
                    log.info(Thread.currentThread().getName() + " 秒杀结果 : " + r.isSuccess() + "，信息:" + r.getMsg() + "，耗时:" + (end - start) + "ms");
                    inventory.showOperateNumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolExecutor.shutdown();
    }

    /**
     * 秒杀核心排队逻辑
     * @param count
     */
    private R commitRequest(String skuId, int count) {
        log.info(Thread.currentThread().getName() + " 下单扣减:" + count);
        R r = new R();
        try {
            // 累计阈值
            incr(skuId);
            // 判断是否达到并发阈值
            if (killConcurrent(skuId)) {
                KillPromise killPromise = addOrCreateSkuQueue(skuId, count);
                try {
                    log.info(Thread.currentThread().getName() + " 挂起等待排队");
                    // 用户线程最多等待200ms
                    killPromise.waitPromise(200);
                    log.info(Thread.currentThread().getName() + " 被唤醒");
                    if (killPromise.isOk()) {
                        r.setSuccess(true);
                    } else {
                        r.setSuccess(false);
                        if (killPromise.getException() != null) {
                            r.setMsg(killPromise.getException().getMessage());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    r.setSuccess(false);
                    r.setMsg("超时");
                }
                return r;
            }
            // 如果没到阈值执行这里
            // 普通单线程直接访问DB
            if (! inventory.deductStock(skuId, count)) {
                r.setMsg("库存不足");
                r.setSuccess(false);
                return r;
            }
            r.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            r.setSuccess(false);
        } finally {
            // 释放阈值
            decr(skuId);
        }
        return r;
    }

    private void decr(String skuId) {
        counter.get(skuId).decrementAndGet();
    }

    /**
     * 避免并发递增
     * @param skuId
     */
    private synchronized void incr(String skuId) {
        AtomicInteger atomicInteger = counter.get(skuId);
        if (atomicInteger == null) {
            counter.put(skuId, new AtomicInteger(1));
        } else {
            atomicInteger.incrementAndGet();
        }
    }

    /**
     * 判断是否创建队列的阈值
     */
    private boolean killConcurrent(String skuId) {
        return Optional.ofNullable(counter.get(skuId)).orElse(new AtomicInteger(0)).get() > 2;
    }

    private final Object lock = new Object();

    /**
     * sku-扣减库存请求
     */
    Map<String, MergeRequestWorker> mergeRequestWorkerMap = new ConcurrentHashMap<>();

    /**
     * 如果秒杀队列不存在就创建，否则直接加入队列
     */
    private KillPromise addOrCreateSkuQueue(String skuId, int count) {
        StockParam stockParam = new StockParam();
        KillPromise killPromise = new KillPromise();
        killPromise.setRequestThread(Thread.currentThread());
        stockParam.setKillPromise(killPromise);
        stockParam.setSkuId(skuId);
        stockParam.setCount(count);

        // 避免并发创建
        synchronized (lock) {
            MergeRequestWorker mergeRequestWorker = mergeRequestWorkerMap.get(skuId);
            if (mergeRequestWorker == null) {
                log.info(Thread.currentThread().getName() + " : ->创建新队列");
                mergeRequestWorker = new MergeRequestWorker();
                mergeRequestWorkerMap.put(skuId, mergeRequestWorker);
            } else {
                log.info(Thread.currentThread().getName() + ", 已存在队列，加入队列");
            }
            mergeRequestWorker.createOrAddStockParam(stockParam);
        }
        return killPromise;
    }

}

@Data
class R {
    private boolean success;

    private String msg;
}

@Data
@Slf4j
class Inventory {
    private String skuId;
    private Integer availNum;
    private int operateNum;

    public Inventory(Integer availNum) {
        this.availNum = availNum;
        this.skuId="1";
    }

    /**
     * 扣减库存， synchronized 关键字模拟行锁
     * 模拟只有库存不足才会返回false
     */
    public synchronized boolean deductStock(String materialId, Integer count) {
        operateNum++;
        log.info(Thread.currentThread().getName() + " : 剩余库存 ： " + this.availNum + ",扣减：" + count);
        if (this.availNum >= count && this.skuId.equals(materialId)) {
            this.availNum = availNum - count;
            return true;
        }
        return false;
    }

    public void showOperateNumer() {
        log.info(Thread.currentThread().getName() + " : 一共操作了:" + operateNum + "次库存记录, 剩余库存 : " + this.availNum);
    }
}

@ToString
@Getter
@Setter
class KillPromise {
    private Exception exception;

    private boolean ok = true;

    private Thread requestThread;

    public synchronized void notifyRequest() {
        super.notify();
    }


    /**
     * 用户线程阻塞等待
     */
    public synchronized void waitPromise(int maxWaitTime) throws InterruptedException {
        wait(maxWaitTime);
    }
}


@ToString
@Getter
@Setter
class StockParam {
    /**
     * 用户线程引用
     */
    private KillPromise killPromise;

    /**
     * 目标skuId
     */
    private String skuId;

    /**
     * 扣减数量
     */
    private Integer count;

}

@Slf4j
class MergeRequestWorker implements Runnable{
    /**
     * 用户线程队列
     */
    LinkedBlockingQueue<StockParam> stockParamQueue = new LinkedBlockingQueue<>(10);
    Inventory inventory;

    public void createOrAddStockParam(StockParam stockParam) {
        stockParamQueue.add(stockParam);
        // 起步执行轮训队列
        Thread thread = new Thread(this);
        thread.setName("merge-request-" + System.currentTimeMillis() % 100 + RandomUtils.nextInt(0, 100));
        thread.start();
    }

    @Override
    public void run() {
        List<StockParam> stockParamList = new ArrayList<>();
        while (true) {
            try {
                // 队列为空等待100ms
                if (stockParamQueue.isEmpty()) {
                    Thread.sleep(100);
                    if (stockParamQueue.isEmpty()) {
                        return ;
                    }
                }

                int size = 0;
                while (true) {
                    StockParam poll = stockParamQueue.poll();
                    // 10个请求一起合并
                    if (poll != null && size <= 10) {
                        stockParamList.add(poll);
                        size++;
                    } else {
                        // 队列为空或则达到最大批量，先执行
                        break;
                    }
                }
                if (stockParamList.isEmpty()) {
                    continue;
                }

                log.info(Thread.currentThread().getName() + " , 合并线程 : " + stockParamList);
                mergeOperateStock(stockParamList);
                stockParamList.clear();
            } catch (InterruptedException e) {
                if (! stockParamList.isEmpty()) {
                    stockParamList.forEach(param -> {
                        param.getKillPromise().setOk(false);
                        param.getKillPromise().notifyRequest();
                    });
                }
            }
        }
    }

    /**
     * 合并执行用户多个线程
     * @param stockParamList
     */
    private void mergeOperateStock(List<StockParam> stockParamList) {
        String skuId = stockParamList.get(0).getSkuId();
        // 合并数量
        Integer count = stockParamList.stream().mapToInt(StockParam::getCount).sum();
        boolean success = KillService.inventory.deductStock(skuId, count);
        if (success) {
            // 合并成功
            log.info(Thread.currentThread().getName() + " 合并扣减成功，通知用户线程");
            stockParamList.forEach(e -> e.getKillPromise().notifyRequest());
        } else {
            // 合并不成功，可以拆分用户线程分别执行
            // 按照扣减数量依次扣减
            log.info(Thread.currentThread().getName() + " 开始合并后的单独扣减");

            // 排序，让扣减数量少的先扣减，尽量满足最多请求量成功
            stockParamList=stockParamList.stream().sorted(Comparator.comparing(StockParam::getCount)).collect(Collectors.toList());

            /*
             逐个执行用户线程，退化为单线程
             */
            for (StockParam stockParam : stockParamList) {
                if (KillService.inventory.deductStock(stockParam.getSkuId(), stockParam.getCount())) {
                    stockParam.getKillPromise().notifyRequest();
                } else {
                    KillPromise killPromise = stockParam.getKillPromise();
                    killPromise.setException(new Exception("合并逐序扣减库存不足"));
                    killPromise.setOk(false);
                    log.info(Thread.currentThread().getName() + " 合并逐序扣减库存不足 : " + stockParam);
                    stockParam.getKillPromise().notifyRequest();
                }
            }
        }
    }
}