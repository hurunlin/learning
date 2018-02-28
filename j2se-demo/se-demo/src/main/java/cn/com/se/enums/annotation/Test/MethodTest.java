package cn.com.se.enums.annotation.Test;

import java.lang.annotation.*;

/**
 * 应用于方法的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodTest {

    /**
     * 注解值
     * @return
     */
    String name() default "胡润林";
    String age() default "24";

}
