package MasterWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 主控类
 */
public class Master {

    /**
     * 一:任务队列
     * 1:非阻塞的队列.支持高并发(承装任务的集合)
     */
    private ConcurrentLinkedQueue<Object> workerQueue = new ConcurrentLinkedQueue<Object>();

    /**
     * 二:使用hashMap去承装所有worker
     * worker进程队列
     */
    private HashMap<String, Thread> map = new HashMap<String, Thread>();

    /**
     * 三:使用一个容器承装每一个worker执行的结果
     * 并发统计结果集合(会有多个worker进行更新)
     * 1:会有多个morker进行结果汇总,那么就会存在并发问题.
     */
    private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

    /**
     * 四:构造方法
     * 1:实现构造方法 worker实例对象,一共多少个线程执行
     * 2:Master的构造，需要一个Worker进程逻辑，和需要Worker进程数量
     */
    public Master(Worker worker, int countWorker) {
        // 将队列给worker对象
        worker.setResultMap(resultMap);
        // 将result集合给worker
        worker.setWorkerQueue(workerQueue);
        // 根据最大任务线程数
        for (int i = 0; i < countWorker; i++) {
            // 将任务传给进程队列(map)
            map.put(Integer.toString(i), new Thread(worker, Integer.toString(i)));
        }
    }


    /**
     * 五:提交任务到队列中
     * 提交一个任务(将一个任务加入到任务队列中)
     * @param job
     */
    public void submit(Object job) {
        workerQueue.add(job);
    }

    /**
     * 六:启动所有worker的方法
     * 执行所有Worker任务
     */
    public void excute() {
        for (Map.Entry<String, Thread> entry : map.entrySet()) {
            entry.getValue().start();
        }
    }

    /**
     * 七:判断任务是否完成
     * 判断任务集合是否执行完成
     */
    public boolean isComplete() {
        // 循环集合内所有元素,并且判断状态是否是已经终结(TERMINATED)的线程.
        for (Map.Entry<String, Thread> entry : map.entrySet()) {
            if(entry.getValue().getState() != Thread.State.TERMINATED){
                return false;
            }
        }
        return true;
    }

    /**
     * 八:获取返回结果集合方法
     * 返回已经统计完成的结果集合
     * @return
     */
    public Map<String,Object> getResultMap(){
        return resultMap;
    }


}
