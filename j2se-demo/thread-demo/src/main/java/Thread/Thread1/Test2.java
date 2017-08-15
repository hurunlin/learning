package Thread.Thread1;

/**
 * Created by hurunlin on 2017/8/10.
 */
public class Test2 extends Thread {

    //设置类静态变量,各线程访问这同一共享变量
    private boolean flag = false;


    //无限循环,等待flag变为true时才跳出循环
    public void run() {
        while (!flag) {
            System.out.println("1111111");
        };
    }

    private void test() {
        flag = true;
    }

    public static void main(String[] args) throws Exception {
        Test2 test = new Test2();
        test.start();
        //sleep的目的是等待线程启动完毕,也就是说进入run的无限循环体了
        Thread.sleep(100);
        test.test();
    }
}
