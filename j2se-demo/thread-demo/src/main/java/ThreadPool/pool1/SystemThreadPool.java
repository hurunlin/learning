/**
 * Project Name: 杉德宝(SMP)
 * File Name: AbstractRejectedExecutionHandler.java
 * Package Name: cn.com.sand.smp.uc.client.threadpool
 * Date: 2016年11月10日上午10:25:11
 * Copyright (c) 2015, 杉德巍康企业服务有限公司.
 * @author fan.xl
 */
package ThreadPool.pool1;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SystemThreadPool <br>
 * Function: 系统框架相关处理线程池. <br>
 * Date: 2015年11月9日 下午2:57:07 <br>
 */
public final class SystemThreadPool extends AbstractThreadPool {

	// 池属性定义
	private final static int corePoolSize = 0;
	private final static int maximumPoolSize = 50;
	private final static long keepAliveTime = 10L;

	// 线程工厂
	private final static ThreadFactory systemThreadFactory = new SystemThreadFactory();
	// 异常处理
	private final static RejectedExecutionHandler systemRejectedExecutionHandler = new SystemRejectedExecutionHandler();

	// 单例池
	private SystemThreadPool() {
		super(corePoolSize, maximumPoolSize, keepAliveTime, 
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), systemThreadFactory, systemRejectedExecutionHandler);
	}
	private final static SystemThreadPool systemThreadPool = new SystemThreadPool();
	public static SystemThreadPool getSingleton() {
		return systemThreadPool;
	}
}