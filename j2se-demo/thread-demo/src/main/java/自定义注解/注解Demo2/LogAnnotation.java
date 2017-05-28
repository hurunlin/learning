package 自定义注解.注解Demo2;

import java.lang.annotation.*;

/**
 * 日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
	
	/** 设置模块*/
	public LogModuleEnum module();
	
	/** 设置操作类型*/
	public LogTypeEnum type();
	
	/** 操作内容*/
	public String doContent() default "";
	
}
