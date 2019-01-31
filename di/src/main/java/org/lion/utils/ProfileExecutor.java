package org.lion.utils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by more on 2016-05-03 20:09.
 */
public class ProfileExecutor extends ThreadPoolExecutor {
    Map<String, Long> startTimes = new ConcurrentHashMap<String, Long>();

    public static ExecutorService newProfileExecutor() {
        return new ProfileExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public ProfileExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ProfileExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                           TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ProfileExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                           TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ProfileExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                           TimeUnit unit, BlockingQueue<Runnable> workQueue,
                           ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTimes.put(String.valueOf(r.hashCode()), System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Long start = startTimes.get(String.valueOf(r.hashCode()));
        Commons.log("Task %-20s Duration: %-10d\n", r.toString(), System.currentTimeMillis() - start);
    }
}
