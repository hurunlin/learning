/**
 * Project Name: learning
 * File Name: TaskBean.java
 * Package Name: ThreadPool.Test
 * Date: 2017-06-30 16:24
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package ThreadPool.Test;

import java.util.List;
import java.util.Map;

/**
 * ClassName: TaskBean <br>
 * Function: <br>
 * Date:  2017-06-30 16:24 <br>
 */
public class TaskBean {

    // 生成文件时间
    private String dateTime;
    // 生成文件路径
    private String genPath;
    // 生成文件路径
    private String ftpPath;
    private String ftpPort;
    private String ftpUser;
    private String ftpPwd;
    private String version;
    // 生成文件具体数据
    private List<Map<String, Object>> taskList;

    public TaskBean() {
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGenPath() {
        return genPath;
    }

    public void setGenPath(String genPath) {
        this.genPath = genPath;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Map<String, Object>> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Map<String, Object>> taskList) {
        this.taskList = taskList;
    }
}
