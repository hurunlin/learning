/**
 * Project Name: learning
 * File Name: ThreadPoolTest.java
 * Package Name: ThreadPool.Test
 * Date: 2017-06-30 15:11
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package ThreadPool.Test;

import java.util.Map;

/**
 * ClassName: ThreadPoolTest <br>
 * Function: <br>
 * Date:  2017-06-30 15:11 <br>
 */
public class ThreadPoolTask implements Runnable {

    private TaskBean taskBean;

    private Map<String, Object> map;


    /**
     * 根据时间，商户号和产品生成对账单
     */
    public ThreadPoolTask(TaskBean bean, Map<String, Object> map) {
        this.taskBean = bean;
        this.map = map;
    }


    public void generateFile() {
        System.out.println("生成对账文件！");
    }

    public void inputFile() {
        System.out.println("写对账文件！");
    }

    @Override
    public void run() {
        System.out.println("-------------------- 开始生成对账文件 -------------------- ");
        this.generateFile();
        this.inputFile();
        System.out.println("-------------------- 对账文件生成完成 -------------------- ");
    }
}
