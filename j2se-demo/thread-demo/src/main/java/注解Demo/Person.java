/**
 * Project Name: demo-test
 * File Name: Person.java
 * Package Name: 自定义注解.注解Demo
 * Date: 2017-05-11 18:10
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package 注解Demo;

/**
 * ClassName: Person <br>
 * Function: <br>
 * Date:  2017-05-11 18:10 <br>
 */
@FieldMeta
public interface Person {
    @FieldMeta
    public void getName();
    @FieldMeta
    public void getId();
}
