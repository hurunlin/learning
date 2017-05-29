/**
 * @Title: ThreadLock
 * @Package LockTest
 * @Description: Lock测试实现
 * @author hu.rl
 * @date 2017/5/29 20:41
 * @version V1.0
 */
package LockTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hu.rl
 * @ClassName: ThreadLock
 * @Description: Lock测试实现
 * @date 2017/5/29 20:41
 */
public class ThreadLock implements Runnable {

    // 创建锁
    public ReentrantLock lock = new ReentrantLock();

    // 创建共享锁
    public Condition condition = lock.newCondition();


    /**
     * @throws
     * @Description: [重写run方法]
     * @date 2017/5/29  20:41
     */
    @Override
    public void run() {
        // T1线程执行method1方法T2执行method2方法
        if ("t1".equals(Thread.currentThread().getName())) {
            this.method1();
        } else {
            this.method2();
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @Description: [实现类1]
     * @date 2017/5/29  20:42
     */
    public void method1() {
        // 获取锁
        lock.lock();
        System.out.println("执行 method1-开始");
        try {
            Thread.sleep(3000);
            // 睡眠该线程
            System.out.println("method 1释放锁");
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("method 1执行完成");
    }

    /**
     * @param
     * @return
     * @throws
     * @Description: [实现类2]
     * @date 2017/5/29  20:42
     */
    public void method2() {
        // 获取锁
        try {
            lock.lock();
            System.out.println("执行 method2-开始");
            // 唤醒线程1
            Thread.sleep(3000);
            condition.signal();
            System.out.println("method 2执行完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
