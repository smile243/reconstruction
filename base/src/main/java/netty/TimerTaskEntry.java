package netty;

import lombok.Data;


/**
 * @Description: 存储要执行的定时任务
 * @author: yjl
 * @date: 2022年07月19日 13:47
 */
@Data
public class TimerTaskEntry implements Comparable<TimerTaskEntry> {
    private TimerTask timerTask;
    private long expireMs;
    volatile TimerTaskList timerTaskList;
    TimerTaskEntry next;
    TimerTaskEntry prev;

    public TimerTaskEntry(TimerTask timerTask, long expireMs) {
        this.timerTask = timerTask;
        this.expireMs = expireMs;
        this.next = null;
        this.prev = null;
    }

    void remove() {
        TimerTaskList currentList = timerTaskList;
        while (currentList != null) {
            currentList.remove(this);
            currentList = timerTaskList;
        }
    }

    @Override
    public int compareTo(TimerTaskEntry o) {
        return (int) (this.expireMs - o.getExpireMs());
    }
}
