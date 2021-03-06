package cn.com.hrl.core.database.mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解注释在service方法上，标注为链接slaves库
 * Created by Jason on 2017/3/6.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
// 在运行时反射获取
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnlyConnection {
}
