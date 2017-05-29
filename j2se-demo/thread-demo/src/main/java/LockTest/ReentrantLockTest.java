/**
 * @Title: ReentrantLockTest
 * @Package LockTest
 * @Description: 测试lock锁机制
 * @author hu.rl
 * @date 2017/5/29 20:40
 * @version V1.0
 */
package LockTest;

/**
 * @ClassName: ReentrantLockTest
 * @Description: 测试lock锁机制
 * @author hu.rl
 * @date 2017/5/29 20:40
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        // 创建实现对象
        ThreadLock threadLock = new ThreadLock();
        // 创建两个线程
        Thread t1 = new Thread(threadLock,"t1");
        Thread t2 = new Thread(threadLock,"t2");
        // 执行两个线程
        t1.start();
        t2.start();
    }
}
