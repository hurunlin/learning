package ThreadPool.ThreadTest2;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by moque on 17/5/17.
 */
public class MyQueue {
    // 创建队列
    public LinkedList<Object> queue = new LinkedList<Object>();
    // 计数器
    public AtomicInteger count = new AtomicInteger();
    // 最小长度
    public static int minSize = 0;
    // 最大长度
    public static int maxSize;

    public Object lock = new Object();

    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    // 添加元素
    public void put(Object object) {
        synchronized (lock) {
            while (count.get() == maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            // 1 添加元素
            queue.add(object);
            // 2 计数器++
            count.incrementAndGet();
            System.out.println("添加元素:" + object);
            // 当有元素的情况下通知tack --
            lock.notify();
        }
    }

    // 移除元素
    public Object take() {
        Object obj = null;
        synchronized (lock) {
            while (count.get() == minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            //1 删除元素
            obj = queue.removeFirst();
            //2 计数器--
            count.decrementAndGet();
            System.out.println("移除元素:" + obj);
            //3 通知添加元素线程进行元素添加
            lock.notify();
        }
        return obj;
    }


    public void getSize() {
        System.out.println("当前长度为:" + count);
    }


    public static void main(String[] args) {
        final MyQueue queue = new MyQueue(5);
        queue.put("a");
        queue.put("b");
        queue.put("c");
        queue.put("d");
        queue.put("e");

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                queue.put("f");
                queue.put("g");
            }
        }, "t1");

        t1.start();


        Thread t2 = new Thread(new Runnable() {
            public void run() {
                queue.take();
                queue.take();
            }
        }, "t2");

        t2.start();


    }

}
