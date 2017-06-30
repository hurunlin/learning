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
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName: AbstractRejectedExecutionHandler <br>
 * Function: 线程拒绝提交的抽象实现 <br>
 * Date: 2015年11月10日 上午10:25:11 <br>
 */
public abstract class AbstractRejectedExecutionHandler implements RejectedExecutionHandler {
	
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    }
}