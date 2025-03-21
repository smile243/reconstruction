package netty;

import java.util.concurrent.DelayQueue;

/**
 * @Description: 时间轮
 * @author: yjl
 * @date: 2022年07月19日 13:17
 */
public class TimeWheel {
    /***
     * 时间轮最小刻度
     */
    private long tickMs;

    /***
     * 槽的大小
     */
    private int wheelSize;

    /***
     * 一轮时间总长
     */
    private long interval;

    private long currentTime;

    /***
     * 槽
     */
    private TimerTaskList[] buckets;

    /**
     * 上层时间轮
     */
    private volatile TimeWheel overflowWheel;

    private DelayQueue<TimerTaskList> delayQueue;

    public TimeWheel(long tickMs, int wheelSize, long currentTime, DelayQueue<TimerTaskList> delayQueue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.currentTime = currentTime;
        this.delayQueue = delayQueue;
        this.interval = tickMs * wheelSize;
        this.buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new TimerTaskList();
        }
        this.currentTime = currentTime - (currentTime % tickMs);
    }

    public boolean add(TimerTaskEntry entry) {
        long expiration = entry.getExpireMs();
        if (expiration < tickMs + currentTime) {
            //时间过了
            return false;
        } else if (expiration < currentTime + interval) {
            //
            long virtualId = expiration / tickMs;
            int index = (int) (virtualId / wheelSize);
            TimerTaskList bucket = buckets[index];
            bucket.addTask(entry);
            if (bucket.setExpiration(virtualId * tickMs)) {
                delayQueue.offer(bucket);
                return true;
            }
        } else {
            TimeWheel timeWheel = getOverflowWheel();
            return timeWheel.add(entry);
        }
        return false;
    }

    private TimeWheel getOverflowWheel() {
        if (overflowWheel == null) {
            synchronized (this) {
                if (overflowWheel == null) {
                    overflowWheel = new TimeWheel(interval, wheelSize, currentTime, delayQueue);
                }
            }
        }
        return overflowWheel;
    }

    public void advanceLock(long timestamp) {
        if (timestamp > currentTime + tickMs) {
            currentTime = timestamp - (timestamp % tickMs);
            if (overflowWheel != null) {
                this.getOverflowWheel().advanceLock(timestamp);
            }
        }
    }
}
