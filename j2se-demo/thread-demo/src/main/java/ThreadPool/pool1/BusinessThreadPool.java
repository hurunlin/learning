/**
 * Project Name: 杉德宝(SMP)
 * File Name: AbstractRejectedExecutionHandler.java
 * Package Name: cn.com.sand.smp.uc.client.threadpool
 * Date: 2016年11月10日上午10:25:11
 * Copyright (c) 2015, 杉德巍康企业服务有限公司.
 *
 * @author fan.xl
 */
package ThreadPool.pool1;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: BusinessThreadPool <br>
 * Function: 业务处理线程池. <br>
 * Date: 2015年11月9日 下午2:56:08 <br>
 */
public final class BusinessThreadPool extends AbstractThreadPool {

    // 池属性定义
    private final static int corePoolSize = 0;
    private final static int maximumPoolSize = 200;
    private final static long keepAliveTime = 10L;

    // 线程工厂
    private final static ThreadFactory businessThreadFactory = new BusinessThreadFactory();

    // 异常处理
    private final static RejectedExecutionHandler businessRejectedExecutionHandler = new BusinessRejectedExecutionHandler();

    // 单例池
    private BusinessThreadPool() {
        super(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), businessThreadFactory, businessRejectedExecutionHandler);
    }

    private final static BusinessThreadPool businessThreadPool = new BusinessThreadPool();

    public static BusinessThreadPool getSingleton() {
        return businessThreadPool;
    }
}
