/**
 * Project Name: demo-test
 * File Name: FutureTask.java
 * Package Name: JDKFuture
 * Date: 2017-05-23 14:53
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package JDKFuture;

import java.util.concurrent.Callable;

/**
 * ClassName: FutureTask <br>
 * Function: <br>
 * Date:  2017-05-23 14:53 <br>
 */
public class FutureTask1 implements Callable {

    @Override
    public Object call() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello word!";
    }
}
