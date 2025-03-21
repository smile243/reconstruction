package netty;


import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年07月19日 15:25
 */
public class SystemTimer implements Timer{
    //时间轮
    private TimeWheel timeWheel;

    //延时队列
    private final DelayQueue<TimerTaskList> delayQueue=new DelayQueue<>();
    /**
     * 过期任务执行线程
     */
    private final ExecutorService workerThreadPool;
    /**
     * 轮询delayQueue获取过期任务线程
     */
    private final ExecutorService bossThreadPool;


    public SystemTimer() {
        this.timeWheel = new TimeWheel(1, 20, System.currentTimeMillis(), delayQueue);
        this.workerThreadPool = Executors.newFixedThreadPool(100);
        this.bossThreadPool = Executors.newFixedThreadPool(1);
        //20ms推动一次时间轮运转
        this.bossThreadPool.submit(() -> {
            for (; ; ) {
                this.advanceClock(20);
            }
        });
    }


    public void addTimerTaskEntry(TimerTaskEntry entry) {
        if (!timeWheel.add(entry)) {
            //已经过期了
            TimerTask timerTask = entry.getTimerTask();
            workerThreadPool.submit(timerTask);
        }
    }

    @Override
    public void add(TimerTask timerTask) {
        TimerTaskEntry entry = new TimerTaskEntry(timerTask, timerTask.getDelayMs() + System.currentTimeMillis());
        timerTask.setTimerTaskEntry(entry);
        addTimerTaskEntry(entry);
    }

    /**
     * 推动指针运转获取过期任务
     *
     * @param timeout 时间间隔
     * @return
     */
    @Override
    public synchronized void advanceClock(long timeout) {
        try {
            TimerTaskList bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                //推进时间
                timeWheel.advanceLock(bucket.getExpiration());
                //执行过期任务(包含降级)
                bucket.clear(this::addTimerTaskEntry);
            }
        } catch (InterruptedException e) {

        }
    }

    @Override
    public int size() {
        //获取等待执行的任务数
        return 0;
    }

    @Override
    public void shutdown() {
        this.bossThreadPool.shutdown();
        this.workerThreadPool.shutdown();
        this.timeWheel = null;
    }
}
