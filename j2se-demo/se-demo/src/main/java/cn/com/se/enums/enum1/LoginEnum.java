/**
 * Project Name: learning
 * File Name: LoginEnum.java
 * Package Name: enums.enum1
 * Date: 2017-06-19 17:32
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.se.enums.enum1;

/**
 * ClassName: LoginEnum <br>
 * Function: <br>
 * Date:  2017-06-19 17:32 <br>
 */
public enum LoginEnum {
    QQLOGIN("qqLogin"), SINALOGIN("sinaLogin");

    private String type;

    private LoginEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
