package ThreadPool.ThreadTest1;

/**
 * Created by moque on 17/4/3.
 */
public class MyObject {

    public synchronized  void monthd1(){
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    public synchronized void method2(){
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        final MyObject object = new MyObject();
        Thread threa1 = new Thread(new Runnable() {
            public void run() {
                object.monthd1();
            }
        },"t1");
        Thread threa2 = new Thread(new Runnable() {
            public void run() {
                object.method2();
            }
        },"t2");
        threa1.start();
        threa2.start();
    }
}
