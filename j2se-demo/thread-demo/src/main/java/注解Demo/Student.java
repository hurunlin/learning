/**
 * Project Name: demo-test
 * File Name: Student.java
 * Package Name: 自定义注解.注解Demo
 * Date: 2017-05-11 18:12
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package 注解Demo;

/**
 * ClassName: Student <br>
 * Function: <br>
 * Date:  2017-05-11 18:12 <br>
 */
@FieldMeta
public class Student implements Person {
    @FieldMeta(name="增加")
    public void getName() {

    }

    @FieldMeta(id = false,description = "获取ID")
    public void getId() {

    }
}
