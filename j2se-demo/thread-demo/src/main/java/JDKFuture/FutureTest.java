/**
 * Project Name: demo-test
 * File Name: FutureTest.java
 * Package Name: JDKFuture
 * Date: 2017-05-23 14:48
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package JDKFuture;

import java.util.concurrent.*;

/**
 * ClassName: FutureTest <br>
 * Function: <br>
 * Date:  2017-05-23 14:48 <br>
 */
public class FutureTest {

    public static void main(String[] args) {
        FutureTask<String> task1 = new FutureTask<String>(new FutureTask1());
        FutureTask<String> task2 = new FutureTask<String>(new FutureTask2());

        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Ready");
        /**
         * 1、submit可以传去实现Callable接口的对象
         * 2、submit方法有返回值，execter没有返回值
         * 3、get方法返回任务是否执行完毕，返回null则表示执行完成
         */
        Future strFuture1 = executor.submit(task1);
        Future strFuture2 = executor.submit(new FutureTask2());
        Future strFuture3 = executor.submit(new FutureTask2());


        try {
            // git方法异步调用
            System.out.println("Give the future" + task1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Get the future : " + strFuture1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Get the future : " + task2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("End");
        executor.shutdown();
    }
}
