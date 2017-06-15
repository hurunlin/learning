package Executor;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by moque on 17/5/26.
 */
public class BoundeBuffer {

    // 定义重入锁
    private ReentrantLock lock = new ReentrantLock();

    // 定义最大任务处理数
    public int taskMax = 0;
    // 定义最小任务处理数
    public AtomicInteger count = new AtomicInteger(0);

    // 定义共享锁
    Condition condition1 = lock.newCondition();

    // 定义非阻塞队列
    private ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<Object>();

    /**
     * 构造方法
     * @param taskMax
     */
    public BoundeBuffer(int taskMax){
        this.taskMax = taskMax;
    }


    /**
     * 取出元素
     *
     * @return
     */
    public Object take() {
        lock.lock();
        Object obj = null;
        try {
            // 如果队列中任务数量已经有
            if (queue.size() == taskMax) {
                condition1.await();
            }
            // 从队列中减减
            obj = queue.poll();
            // 统计值减减
            count.decrementAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }

    /**
     * 添加元素
     *
     * @param object
     */
    public boolean put(Object object) {
        lock.lock();

        try {
            System.out.println("最大元素个数为:"+taskMax);
            System.out.println("当前添加元素个数为:"+queue.size());
            // 如果队列中存在元素那么通知take线程开始取.
            if (queue.size() > count.get()) {
                condition1.signal();
            }
            // 当发生大并发情况下,取的线程执行不那么快时那么就拒绝添加元素
            if (queue.size() >= taskMax * 0.7) {
                System.out.println("队列中元素已经达到瓶颈不能够再添加元素");
                return false;
            }
            // 将元素放到队列中
            queue.add(object);
            // 将最小的任务数加加
            count.incrementAndGet();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }


}
