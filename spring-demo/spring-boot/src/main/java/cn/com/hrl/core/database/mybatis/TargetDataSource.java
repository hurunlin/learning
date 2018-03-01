/**
 * @Title: TargetDataSource
 * @Package cn.com.hrl.database.mybatis
 * @Description: 动态数据源注册
 * @author hu.rl
 * @date 2017/6/10 15:39
 * @version V1.0
 */
package cn.com.hrl.core.database.mybatis;

import java.lang.annotation.*;

/**
 * @ClassName: TargetDataSource
 * @Description: 动态数据源注册
 * @author hu.rl
 * @date 2017/6/10 15:39
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}
