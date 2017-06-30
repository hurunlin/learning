/**
 * Project Name: learning
 * File Name: TestService.java
 * Package Name: ThreadPool.Test
 * Date: 2017-06-30 15:14
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package ThreadPool.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: TestService <br>
 * Function: <br>
 * Date:  2017-06-30 15:14 <br>
 */
public class TestService {

    public TaskBean taskBean;

    List<Map<String, Object>> merchList = new ArrayList<Map<String, Object>>();

    /**
     * 定义版本枚举
     */
    enum version {
        VERSION_1("v1"), VERSION_2("v2");
        private String versionType;

        private version(String versionType) {
            this.versionType = versionType;
        }

        public String getVersionType() {
            return versionType;
        }

        public void setVersionType(String versionType) {
            this.versionType = versionType;
        }
    }

    /**
     * 查询需要生成对账文件的商户信息
     */
    public void queryClearMerch() {
        // 获取普通商户统计数据
        List<Map<String, Object>> v1List = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : v1List) {
            map.put("version", version.VERSION_1.getVersionType());
            merchList.add(map);
        }
        // 获取平台商户商户统计数据
        List<Map<String, Object>> v2List = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : v2List) {
            map.put("version", version.VERSION_2.getVersionType());
            merchList.add(map);
        }
        taskBean.setTaskList(merchList);
    }

    /**
     * 初始化任务对象
     */
    public void initTaskBean() {
        // 初始化对象
        taskBean.setDateTime("20170630");
        taskBean.setGenPath("D://");
    }


    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        TestService testService = new TestService();
        // 初始化生成对账文件bean
        testService.initTaskBean();
        // 获取对账文件信息
        testService.queryClearMerch();
        // 创建线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 批量处理生成对账文件
        for (Map<String, Object> map : testService.merchList) {
            es.submit(new ThreadPoolTask(testService.taskBean, map));
        }
    }
}
