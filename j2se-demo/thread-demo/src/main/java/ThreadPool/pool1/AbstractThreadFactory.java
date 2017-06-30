/**
 * Project Name: 杉德宝(SMP)
 * File Name: AbstractRejectedExecutionHandler.java
 * Package Name: cn.com.sand.smp.uc.client.threadpool
 * Date: 2016年11月10日上午10:25:11
 * Copyright (c) 2015, 杉德巍康企业服务有限公司.
 * @author fan.xl
 */
package ThreadPool.pool1;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: AbstractThreadFactory <br>
 * Function: 抽象线程工厂. <br>
 * Date: 2015年11月9日 下午7:56:54 <br>
 */
public abstract class AbstractThreadFactory implements ThreadFactory {

    protected ThreadGroup group;
    private final AtomicInteger threadSerialNumber = new AtomicInteger(1);
    protected String namePrefix;
    
    protected boolean daemonThread = false;
    
    public AbstractThreadFactory(String threadGroupName, String threadNamePrefix) {
        SecurityManager s = System.getSecurityManager();
        ThreadGroup parentGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.group = new ThreadGroup(parentGroup, threadGroupName);
        this.namePrefix = threadNamePrefix;
    }
    
    public AbstractThreadFactory(String threadGroupName, String threadNamePrefix, boolean daemonThread) {
        SecurityManager s = System.getSecurityManager();
        ThreadGroup parentGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.group = new ThreadGroup(parentGroup, threadGroupName);
        this.namePrefix = threadNamePrefix;
        this.daemonThread = daemonThread;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r,  this.namePrefix + this.threadSerialNumber.getAndIncrement(), 0);
        t.setDaemon(this.daemonThread);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}