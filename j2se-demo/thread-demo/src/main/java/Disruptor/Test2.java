/**
 * Project Name: demo-test
 * File Name: Test2.java
 * Package Name: Disruptor
 * Date: 2017-04-21 11:24
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package Disruptor;

import com.lmax.disruptor.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: Test2 <br>
 * Function: <br>
 * Date:  2017-04-21 11:24 <br>
 */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBERS = 4;
        EventFactory<TradeTransaction> eventFactory = new EventFactory<TradeTransaction>() {
            public TradeTransaction newInstance() {
                return new TradeTransaction();
            }
        };

        RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(eventFactory, BUFFER_SIZE);

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBERS);


        WorkHandler<TradeTransaction> workHandlers = new TradeTransactionInDBHandler();
        /*
         * 这个类代码很简单的，亲自己看哈！~
         */
        WorkerPool<TradeTransaction> workerPool = new WorkerPool<TradeTransaction>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), workHandlers);

        workerPool.start(executor);

        //下面这个生产8个数据，图简单就写到主线程算了
        for (int i = 0; i < 8; i++) {
            long seq = ringBuffer.next();
            ringBuffer.get(seq).setPrice(Math.random() * 9999);
            ringBuffer.publish(seq);
        }

        Thread.sleep(1000);
        workerPool.halt();
        executor.shutdown();
    }
}
