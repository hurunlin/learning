package cn.com.se.enums.annotation.Test;

import java.lang.annotation.*;

/**
 * @Retention: RetentionPolicy.SOURCE 注解将被编译器丢弃
 * RetentionPolicy.CLASS 注解在class文件中可用，但会被VM丢弃
 * RetentionPolicy.RUNTIME VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TestAnnotation {
    String name();
    String age();
}
