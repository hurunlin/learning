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

import java.util.concurrent.ThreadPoolExecutor;


/**
 * ClassName: SystemRejectedExecutionHandler <br>
 * Function: 系统级线程拒绝提交的异常处理. <br>
 * Date: 2015年11月9日 下午5:11:42 <br>
 */
public class SystemRejectedExecutionHandler extends AbstractRejectedExecutionHandler {

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        super.rejectedExecution(r, e);
        throw new RuntimeException("系统级处理线程失败 rejected from " + e.toString());
    }
}
