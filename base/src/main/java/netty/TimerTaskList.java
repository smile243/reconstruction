package netty;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @Description:
 * @author: yjl
 * @date: 2022年07月19日 13:22
 */
@Data
public class TimerTaskList implements Delayed {

    private TimerTaskEntry root = new TimerTaskEntry(null, -1);

    {
        root.next = root;
        root.prev = root;
    }

    private AtomicLong expiration=new AtomicLong(-1L);

    public long getExpiration(){
        return expiration.get();
    }

    boolean setExpiration(long expirationMs){
        return expiration.getAndSet(expirationMs)!=expirationMs;
    }

    public boolean addTask(TimerTaskEntry entry){
        boolean done= false;
        while (!done){
            entry.remove();
            synchronized (this){
                if(entry.timerTaskList==null){
                    entry.timerTaskList=this;
                    TimerTaskEntry tail=root.prev;
                    entry.prev=tail;
                    entry.next=root;
                    tail.next=entry;
                    root.prev=entry;
                    done=true;
                }
            }
        }
        return true;
    }

    public synchronized void clear(Consumer<TimerTaskEntry> entry){
        TimerTaskEntry head=root.next;
        while (!head.equals(root)){
            remove(head);
            entry.accept(head);
            head=root.next;
        }
        expiration.set(-1L);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return Math.max(0,unit.convert(expiration.get()-System.currentTimeMillis(),TimeUnit.MILLISECONDS));
    }


    @Override
    public int compareTo(Delayed o) {
        if (o instanceof TimerTaskList){
            return Long.compare(expiration.get(),((TimerTaskList) o).expiration.get());
        }
        return 0;
    }

    public void remove(TimerTaskEntry entry) {
        synchronized (this) {
            if (entry.getTimerTaskList().equals(this)) {
                entry.next.prev = entry.prev;
                entry.prev.next = entry.next;
                entry.next = null;
                entry.prev = null;
                entry.timerTaskList = null;
            }
        }
    }
}
