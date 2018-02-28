package cn.com.se.enums.annotation.springaop;

import java.lang.annotation.*;

/**
 * 自定义切面注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyLogAop {
    String requestUrl() ;
}
