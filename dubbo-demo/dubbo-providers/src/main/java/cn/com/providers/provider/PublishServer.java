/**
 * @Title: PublishServer
 * @Package cn.com.providers.provider
 * @Description: 发布服务
 * @author hu.rl
 * @date 2017/6/18 10:49
 * @version V1.0
 */
package cn.com.providers.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName: PublishServer
 * @Description: 发布服务
 * @author hu.rl
 * @date 2017/6/18 10:49
 */
public class PublishServer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "/applicationContext-dubbo.xml" });
        System.out.println("启动");
        context.start();
        System.out.println("启动了");
		/*try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
