package cn.com.se.enums.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

    @MethodTest(name = "刘佳财", age = "25")
    public void test() {
        System.out.println();
    }

    public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        try {
            Test annotationTest2 = new Test();
            //获取AnnotationTest2的Class实例
            Class<Test> c = Test.class;
            // 获取需要处理的方法Method实例
            Method method = c.getMethod("test", new Class[]{});
            //判断该方法是否包含MyAnnotation注解
            if (method.isAnnotationPresent(MethodTest.class)) {
                //获取该方法的MyAnnotation注解实例
                MethodTest myAnnotation = method.getAnnotation(MethodTest.class);
                //执行该方法

                method.invoke(annotationTest2, new Object[]{});
                //获取myAnnotation
                String name = myAnnotation.name();
                System.out.println(name);
            }
            // 获取方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                System.out.println(annotation);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
