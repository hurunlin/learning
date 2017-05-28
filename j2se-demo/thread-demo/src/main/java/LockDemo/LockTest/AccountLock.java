/**
 * Project Name: demo-test
 * File Name: AccountLock.java
 * Package Name: Lock.LockTest
 * Date: 2017-05-12 9:57
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package LockDemo.LockTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: AccountLock <br>
 * Function: <br>
 * Date:  2017-05-12 9:57 <br>
 */
public class AccountLock {

    /**
     * 创建锁，对象先获取锁
     */
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private List<Integer> listBuffer = new ArrayList<Integer>();

    private volatile boolean runFlag = true;

    /**
     * 生产者
     */
    public void produce() {
        int i = 0;
        while (runFlag) {
            lock.lock();
            try {
                if (listBuffer.size() > 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    listBuffer.add(i++);
                    System.out.println(Thread.currentThread().getName() + "添加到队列" + listBuffer.get(listBuffer.size() - 1));
                    condition.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 消费者
     */
    public void consume() {
        while (runFlag) {
            lock.lock();
            try {
                if (listBuffer.size() == 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "从队列中获取" + listBuffer.get(listBuffer.size() - 1));
                    long beginTime = 0;
                    listBuffer.remove(0);
                    beginTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - beginTime < 100) {
                    }
                    condition.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean isRunFlag() {
        return runFlag;
    }

    public void setRunFlag(boolean runFlag) {
        this.runFlag = runFlag;
    }


    public static void main(String[] args) throws InterruptedException {
        final AccountLock lock = new AccountLock();

        // 创建A线程
        Thread product = new Thread(new Runnable() {
            public void run() {
                lock.produce();
            }
        }, "A");

        // 创建B线程
        Thread consume = new Thread(new Runnable() {
            public void run() {
                lock.consume();
            }
        }, "B");

        // 启动线程
        product.start();

        consume.start();

        Thread.sleep(5000);

        lock.setRunFlag(false);

    }
}
