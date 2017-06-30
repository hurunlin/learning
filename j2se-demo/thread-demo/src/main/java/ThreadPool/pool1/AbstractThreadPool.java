/**
 * Project Name: 杉德宝(SMP)
 * File Name: AbstractRejectedExecutionHandler.java
 * Package Name: cn.com.sand.smp.uc.client.threadpool
 * Date: 2016年11月10日上午10:25:11
 * Copyright (c) 2015, 杉德巍康企业服务有限公司.
 * @author fan.xl
 */
package ThreadPool.pool1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * ClassName: AbstractThreadPool <br>
 * Function: 抽象线程池. <br>
 * Date: 2015年11月9日 下午7:36:39 <br>
 */
public abstract class AbstractThreadPool extends ThreadPoolExecutor {

	private static final Logger logger = LoggerFactory.getLogger(AbstractThreadPool.class);

	public AbstractThreadPool(int corePoolSize, int maximumPoolSize,
                              long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
	}

	@Override
	public void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
	}

	@Override
	public void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		printException(r, t);
	}

	private static void printException(Runnable r, Throwable t) {
	    if (t == null && r instanceof Future<?>) {
	        try {
	            Future<?> future = (Future<?>) r;
	            if (future.isDone())
	                future.get();
	        } catch (CancellationException ce) {
	            t = ce;
	        } catch (ExecutionException ee) {
	            t = ee.getCause();
	        } catch (InterruptedException ie) {
	            Thread.currentThread().interrupt();
	        }
	    }
	    if (t != null) {
	    	logger.error(t.getMessage(), t);
	    }
	}
}
