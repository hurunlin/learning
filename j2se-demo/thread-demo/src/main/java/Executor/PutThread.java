package Executor;

/**
 *添加元素
 * 1:添加元素线程
 */
public class PutThread implements Runnable{

    // 添加队列最大资源数
    private int taskMax = 0;


    private  Object object;

    /**
     * 构造方法注入需要添加的对象
     * @param obj
     */
    public PutThread(Object obj,int taskMax){
        this.taskMax = taskMax;
        this.object = obj;
    }

    /**
     * 重写run方法
     */
    public void run() {
        BoundeBuffer boundeBuffer = new BoundeBuffer(taskMax);

        Object obj = boundeBuffer.put(object);
    }
}
