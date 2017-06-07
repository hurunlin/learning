/**
 * @Title: ApplicationTests
 * @Package cn.com.hrl
 * @Description: 123
 * @author hu.rl
 * @date 2017/6/7 21:44
 * @version V1.0
 */
package cn.com.hrl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: ApplicationTests
 * @Description: 测试
 * @author hu.rl
 * @date 2017/6/7 21:44
 */
@RunWith(SpringRunner.class)
@SpringBootConfiguration
@SpringBootTest
public class ApplicationTests {
    @Test
    public void contextLoads() {
        System.out.println("hello world");
    }
}
