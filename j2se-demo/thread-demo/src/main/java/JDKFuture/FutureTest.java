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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ClassName: FutureTest <br>
 * Function: <br>
 * Date:  2017-05-23 14:48 <br>
 */
public class FutureTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Ready");
        Future strFuture1 = executor.submit(new FutureTask1());
        Future strFuture2 = executor.submit(new FutureTask2());
        System.out.println("Give the future");

        try {
            System.out.println("Get the future : " + strFuture1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("End");
        executor.shutdown();
    }
}
