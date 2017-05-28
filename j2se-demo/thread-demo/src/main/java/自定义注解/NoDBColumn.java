package 自定义注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/5/11.
 */
@Target(ElementType.FIELD)
public @interface NoDBColumn {
}
