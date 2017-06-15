package MasterWorker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 具体任务实现
 */
public class Worker implements Runnable{

    // 任务队列用于获得所有子任务
    protected ConcurrentLinkedQueue workerQueue;
    // 子任务处理的结果集(所有的结果集都会存放于此集合中)
    protected ConcurrentHashMap<String,Object> resultMap;

    /**
     * 利用set方法进行注入
     * @param workerQueue
     */
    public void setWorkerQueue(ConcurrentLinkedQueue workerQueue) {
        this.workerQueue = workerQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * 具体任务的实现抽象方法
     * @param input
     * @return
     */
    public Object handle(Object input){
        return input;
    }

    /**
     * 实现线程的run方法
     */
    public void run() {
        while(true){
            // 获取每一个自任务(poll方法取出一个元素并且删除,返回取出元素对象)
            Object input = workerQueue.poll();
            // 当取出元素为空时说明队列中已经没有任务需要处理.
            if(input == null){
                break;
            }
            // 调用具体实现方法
            Object obj = handle(input);
            // input的hashcode为key,返回结果为value
            resultMap.put(Integer.toString(input.hashCode()),obj);

        }
    }

}
