/**
 * Project Name: learning
 * File Name: Test.java
 * Package Name: enums.enum1
 * Date: 2017-06-19 17:32
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.se.enums.enum1;

/**
 * ClassName: Test <br>
 * Function: <br>
 * Date:  2017-06-19 17:32 <br>
 */
public class Test {

    /**
     * 使用枚举类型的参数
     * @param loginEnum
     */
    public static void test(LoginEnum loginEnum) {
        System.out.println(loginEnum.getType());
    }


    public static void main(String[] args) {
        Test.test(LoginEnum.QQLOGIN);
    }
}
