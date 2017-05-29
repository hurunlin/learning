/**
 * @Title: SemaphoreTest
 * @Package ConcurrentUtils
 * @Description: 信号量类
 * @author hu.rl
 * @date 2017/5/29 17:57
 * @version V1.0
 */
package ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author hu.rl 
 * @ClassName: SemaphoreTest
 * @Description: 信号量类
 * @date 2017/5/29 17:57
 */
public class SemaphoreTest {

    /** 
     * @Description: [主函数入口]
     * @param
     * @return 
     * @date 2017/5/29  20:12
     * @throws
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 创建信号量(只允许五个线程同时访问)
        final Semaphore semaphore = new Semaphore(5);
    }
}
