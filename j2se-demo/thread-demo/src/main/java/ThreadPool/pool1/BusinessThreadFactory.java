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
 * ClassName: BusinessThreadFactory <br>
 * Function: 业务线程工厂. <br>
 * Date: 2015年11月9日 下午7:55:16 <br>
 */
public class BusinessThreadFactory extends AbstractThreadFactory {

	private static final String threadGroupName = "BUSINESS_THREAD_GROUP";
	private static final String threadNamePrefix = "business-thread-";
	
	public BusinessThreadFactory() {
    	super(threadGroupName, threadNamePrefix);
    }

	public BusinessThreadFactory(String threadGroupName, String threadNamePrefix) {
    	super(threadGroupName, threadNamePrefix);
    }
	
	public Thread newThread(Runnable r) {
		return super.newThread(r);
	}
	
}
