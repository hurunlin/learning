/**
 * Project Name: demo-test
 * File Name: TestField.java
 * Package Name: 自定义注解.注解Demo
 * Date: 2017-05-11 18:14
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package 注解Demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ClassName: TestField <br>
 * Function: <br>
 * Date:  2017-05-11 18:14 <br>
 */
public class TestField {
    Annotation[] annotation = null;

    public static void main(String[] args) throws ClassNotFoundException {
        new TestField().getAnnotation();
    }

    public void getAnnotation() throws ClassNotFoundException {
        Class<?> stu = Class.forName("自定义注解.注解Demo.Student");//静态加载类
        boolean isEmpty = stu.isAnnotationPresent(自定义注解.注解Demo.FieldMeta.class);//判断stu是不是使用了我们刚才定义的注解接口if(isEmpty){
        annotation = stu.getAnnotations();//获取注解接口中的
        for (Annotation a : annotation) {
            FieldMeta my = (FieldMeta) a;//强制转换成Annotation_my类型
            System.out.println(stu + ":\n" + my.name() + " say: " + my.name() + " my age: " + my.id());
        }
        Method[] method = stu.getMethods();//
        System.out.println("Method");
        for (
                Method m : method)

        {
            boolean ismEmpty = m.isAnnotationPresent(自定义注解.注解Demo.FieldMeta.class);
            if (ismEmpty) {
                Annotation[] aa = m.getAnnotations();
                for (Annotation a : aa) {
                    FieldMeta an = (FieldMeta) a;
                    System.out.println(stu + ":\n" + an.name() + " say: " + an.name() + " my age: " + an.id());
                }
            }
        }
        //get Fields by force
        System.out.println("get Fileds by force !");
        Field[] field = stu.getDeclaredFields();
        for (
                Field f : field)

        {
            f.setAccessible(true);
            System.out.println(f.getName());
        }
        System.out.println("get methods in interfaces !");
        Class<?> interfaces[] = stu.getInterfaces();
        for (
                Class<?> c : interfaces)

        {
            Method[] imethod = c.getMethods();
            for (Method m : imethod) {
                System.out.println(m.getName());
            }
        }
    }
}
