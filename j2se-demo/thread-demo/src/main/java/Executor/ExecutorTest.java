package Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by moque on 17/5/24.
 */
public class ExecutorTest {

    public static void main(String[] args) {
        User user = new User();
        PutThread task = new PutThread(user,100);

        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executor.execute(task);
        }
        // 当线程内任务全部执行完成之后才会结束
        executor.shutdown();


    }
}
