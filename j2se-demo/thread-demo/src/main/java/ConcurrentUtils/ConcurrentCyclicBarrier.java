/**
 * @Title: ConcurrentCyclicBarrier
 * @Package ConcurrentUtils
 * @Description: 并发工具类
 * @author hu.rl
 * @date 2017/5/29 9:45
 * @version V1.0
 */
package ConcurrentUtils;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hu.rl
 * @ClassName: ConcurrentCyclicBarrier
 * @Description: 并发工具类
 * @date 2017/5/29 9:45
 */
public class ConcurrentCyclicBarrier {

    /**
     * @param * @return
     * @throws
     * @Description: [主函数入口]
     * @date 2017/5/29  9:45
     */
    public static void main(String[] args) {
        // new一个cyclicBarrier，3:表示有三个运动员
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 提交任务
        executor.submit(new Runner(cyclicBarrier, "张三"));
        executor.submit(new Runner(cyclicBarrier, "李四"));
        executor.submit(new Runner(cyclicBarrier, "王五"));
        // 关闭线程池
        executor.shutdown();

    }
}
