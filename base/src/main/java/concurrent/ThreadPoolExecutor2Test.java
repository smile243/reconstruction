package concurrent;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.State.BLOCKED;
import static java.lang.Thread.State.TIMED_WAITING;
import static java.lang.Thread.State.WAITING;

/**
 * 收尾:java.util.concurrent.ThreadPoolExecutor#runWorker 1.task.run(); 2.afterExecute(task, thrown); 3.task = null;w.completedTasks++;w.unlock();
 * 就绪:java.util.concurrent.ThreadPoolExecutor#getTask
 * dubbo的JVMUtil可以dump jvm
 *
 * @author yjl
 */
@Slf4j
public class ThreadPoolExecutor2Test {
    private static final ThreadPoolExecutor threadPoolExecutor =
        new ThreadPoolExecutor(64, 64, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<>(32)) {
            //使主线程在子线程收尾前扔任务到队列中
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

    public static void main(String[] args) {
        threadPoolExecutor.setRejectedExecutionHandler(new AbortPolicyWithReport());
        for (int i = 0; i < 100; i++) {
            CountDownLatch countDownLatch = new CountDownLatch(34);
            for (int j = 0; j < 34; j++) {
                int activeCount = threadPoolExecutor.getActiveCount();
                int queueSie = threadPoolExecutor.getQueue().size();
                log.info("当前正在执行任务的线程数里开始:" + activeCount);
                log.info("线程池中阻塞队列的任务数量开始:" + queueSie);
                threadPoolExecutor.execute(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                        log.info("====> countDownlatch =" + "===>" + countDownLatch.getCount());
                    }
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("==========> 详情任务 - 任务处理完成");
        }
        log.info("都执行完成了");
    }

    public static class AbortPolicyWithReport implements RejectedExecutionHandler {
        public AbortPolicyWithReport() {
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try (FileOutputStream jStackStream = new FileOutputStream(new File("E://", "JSTACK_0.log"))) {
                jstack(jStackStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new RejectedExecutionException("TASK " + r.toString() + " reject from " + executor.toString());
        }
    }

    protected static void jstack(FileOutputStream fileOutputStream) throws Exception {
        ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
        for (ThreadInfo threadInfo : threadMxBean.dumpAllThreads(true, true)) {
            fileOutputStream.write(getThreadDumpString(threadInfo).getBytes());
        }
    }

    private static String getThreadDumpString(ThreadInfo threadInfo) {
        StringBuilder sb = new StringBuilder("\"" + threadInfo.getThreadName() + "\"" +
            " Id=" + threadInfo.getThreadId() + " " +
            threadInfo.getThreadState());
        if (threadInfo.getLockName() != null) {
            sb.append(" on " + threadInfo.getLockName());
        }
        if (threadInfo.getLockOwnerName() != null) {
            sb.append(" owned by \"" + threadInfo.getLockOwnerName() +
                "\" Id=" + threadInfo.getLockOwnerId());
        }
        if (threadInfo.isSuspended()) {
            sb.append(" (suspended)");
        }
        if (threadInfo.isInNative()) {
            sb.append(" (in native)");
        }
        sb.append('\n');
        int i = 0;

        StackTraceElement[] stackTrace = threadInfo.getStackTrace();
        MonitorInfo[] lockedMonitors = threadInfo.getLockedMonitors();
        for (; i < stackTrace.length && i < 32; i++) {
            StackTraceElement ste = stackTrace[i];
            sb.append("\tat ").append(ste.toString());
            sb.append('\n');
            if (i == 0 && threadInfo.getLockInfo() != null) {
                Thread.State ts = threadInfo.getThreadState();
                if (BLOCKED.equals(ts)) {
                    sb.append("\t-  blocked on ").append(threadInfo.getLockInfo());
                    sb.append('\n');
                } else if (WAITING.equals(ts) || TIMED_WAITING.equals(ts)) {
                    sb.append("\t-  waiting on ").append(threadInfo.getLockInfo());
                    sb.append('\n');
                }
            }

            for (MonitorInfo mi : lockedMonitors) {
                if (mi.getLockedStackDepth() == i) {
                    sb.append("\t-  locked ").append(mi);
                    sb.append('\n');
                }
            }
        }
        if (i < stackTrace.length) {
            sb.append("\t...");
            sb.append('\n');
        }

        LockInfo[] locks = threadInfo.getLockedSynchronizers();
        if (locks.length > 0) {
            sb.append("\n\tNumber of locked synchronizers = " + locks.length);
            sb.append('\n');
            for (LockInfo li : locks) {
                sb.append("\t- " + li);
                sb.append('\n');
            }
        }
        sb.append('\n');
        return sb.toString();
    }
}




