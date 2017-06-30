/**
 * Project Name: 杉德宝(SMP)
 * File Name: AbstractRejectedExecutionHandler.java
 * Package Name: cn.com.sand.smp.uc.client.threadpool
 * Date: 2016年11月10日上午10:25:11
 * Copyright (c) 2015, 杉德巍康企业服务有限公司.
 * @author fan.xl
 */
package ThreadPool.pool1;

/**
 * ClassName: SystemThreadFactory <br>
 * Function: 系统级线程工厂,用于创建各类系统守候线程. <br>
 * Date: 2015年11月10日 上午10:37:14 <br>
 */
public class SystemThreadFactory extends AbstractThreadFactory {

	private static final String threadGroupName = "SYSTEM_THREAD_GROUP";
	private static final String threadNamePrefix = "system-thread-";
	
	public SystemThreadFactory() {
    	super(threadGroupName, threadNamePrefix, true);
    }

	public SystemThreadFactory(String threadGroupName, String threadNamePrefix) {
    	super(threadGroupName, threadNamePrefix, true);
    }
}
