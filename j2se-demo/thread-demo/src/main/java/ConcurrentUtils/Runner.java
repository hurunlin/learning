/**
 * @Title: Runner
 * @Package ConcurrentUtils
 * @Description: 运动员类
 * @author hu.rl
 * @date 2017/5/29 16:03
 * @version V1.0
 */
package ConcurrentUtils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author hu.rl
 * @ClassName: Runner
 * @Description: 运动员类
 * @date 2017/5/29 16:03
 */
public class Runner implements Runnable {
    /**
     * 定义裁判(可以理解为一个同步器，所有线程都通知准备好的时候才会允许任务执行)
     */
    private CyclicBarrier cyclicBarrier;
    /**
     * 运动员名字
     */
    private String name;

    /**
     * @param cyclicBarrier
     * @param name
     * @Description 构造方法
     */
    public Runner(CyclicBarrier cyclicBarrier, String name) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
    }

    /**
     * @throws
     * @Description: [重写run方法]
     * @date 2017/5/29  16:04
     */
    @Override
    public void run() {
        try {
            /**
             * 1、运动员准备
             * 2、模拟随机休眠0-5秒
             */
            Thread.sleep(1000 * (new Random().nextInt(5)));
            System.out.println(name + "准备完成！");
            // 等待所有任务都准备完成再往下执行
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + "开始跑！");
    }
}
