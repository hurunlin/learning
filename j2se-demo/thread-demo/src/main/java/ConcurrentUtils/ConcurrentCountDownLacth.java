/**
 * @Title: ConcurrentCountDownLacth
 * @Package ConcurrentUtils
 * @Description: 并发工具类
 * @author hu.rl
 * @date 2017/5/29 9:44
 * @version V1.0
 */
package ConcurrentUtils;

import java.util.concurrent.CountDownLatch;

/**
 * @author hu.rl
 * @ClassName: ConcurrentCountDownLacth
 * @Description: 并发工具类
 * @date 2017/5/29 9:44
 */
public class ConcurrentCountDownLacth {


    /**
     * @param
     * @return
     * @throws
     * @Description: [主函数入口]
     * @date 2017/5/29  15:14
     */
    public static void main(String[] args) {
        /**
         * 1: 创建在线程中使用的countDown创建在线程中使用的countDown
         * 2: 参数(1)需要有多少个任务需要初始化完成,1则需要有一个线程调用countDown,2则需要有两个线程调用countDown
         * 主线程才会继续往下执行
         */
        final CountDownLatch countDown = new CountDownLatch(1);
        // 创建线程1
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程T1 等待其他线程处理完成");
                try {
                    countDown.await();
                    System.out.println("T1线程继续执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 创建线程2
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("T2线程进行初始化操作");
                try {
                    Thread.sleep(5000);
                    System.out.println("T2线程业务处理完成");
                    // 通知T1线程继续执行
                    countDown.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        t1.start();
        t2.start();
    }
}
