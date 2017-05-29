/**
 * @Title: ReentrantReadWriteLockTest
 * @Package LockTest
 * @Description: 读写锁实现
 * @author hu.rl
 * @date 2017/5/29 21:40
 * @version V1.0
 */
package LockTest;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hu.rl
 * @ClassName: ReentrantReadWriteLockTest
 * @Description: 读写锁实现
 * @date 2017/5/29 21:40
 */
public class ReentrantReadWriteLockTest {

    /**
     * 1、通过读写分离的方式实现
     * 2、读读共享，写写互斥，读写互斥
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // 获取读锁
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    // 获取写锁
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * @throws
     * @Description: [实现读的方法]
     * @date 2017/5/29  21:43
     */
    public void read() {
        // 获取读锁
        readLock.lock();
        try {
            System.out.println("当前线程："+Thread.currentThread().getName()+" 进入..." );
            Thread.sleep(3000);
            System.out.println("当前线程："+Thread.currentThread().getName()+" 退出..." );
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }


    /**
     * @throws
     * @Description: [实现读的方法]
     * @date 2017/5/29  21:43
     */
    public void write() {
        // 获取读锁
        writeLock.lock();
        try {
            System.out.println("当前线程："+Thread.currentThread().getName()+" 进入..." );
            Thread.sleep(3000);
            System.out.println("当前线程："+Thread.currentThread().getName()+" 退出..." );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }


    public static void main(String[] args) {
        final ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.read();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.read();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.write();
            }
        });

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.write();
            }
        });
        //t1.start();
        //t2.start();
        t3.start();
        t4.start();
    }
}
