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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * ClassName: BusinessRejectedExecutionHandler <br>
 * Function: 业务线程拒绝提交的异常处理. <br>
 * Date: 2015年11月10日 上午10:30:07 <br>
 */
public class BusinessRejectedExecutionHandler extends AbstractRejectedExecutionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BusinessRejectedExecutionHandler.class);

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        super.rejectedExecution(r, e);
        logger.error("业务单元{}提交BusinessThreadPool失败{},尝试重试提交", r.toString(), e.toString());

        // 重试40次提交
        int i = 40;
        do {
            try {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                }
                logger.warn("业务单元{}第{}次提交BusinessThreadPool重试", r.toString(), i);
                e.execute(r);
                logger.warn("业务单元{}第{}次提交BusinessThreadPool重试成功", r.toString(), i);
                break;
            } catch (RuntimeException x) {
                i = i - 1;
            }
        } while (i > 0);

        if (i == 0) {
            throw new RuntimeException("业务单元" + r.toString() +
                    "尝试40次提交BusinessThreadPool失败:" + e.toString());
        }
    }
}

