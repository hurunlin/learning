package ThreadPool.ThreadTest1;

/**
 * Created by moque on 17/4/2.
 */
public class MutiThread {

    private  int num = 0;


    /**
     * 每一个对象都有一把锁,当加了 synchronized关键字的时候会获取锁(排它锁)
     * @param tag
     */
    public  synchronized  void printNum(String tag){

        System.out.println();
        try{

            if("a".equals(tag)){
                num = 100;
                System.out.println("tag =====a----number赋值为100");
                Thread.sleep(1000);
            }else{
                num = 200;
                System.out.println("tag =====b----number赋值为200");
            }
            System.out.println("tag ====="+tag+"----number赋值为"+num);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        final MutiThread mutiThread1 = new MutiThread();
        final MutiThread mutiThread2 = new MutiThread();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                mutiThread1.printNum("a");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                mutiThread1.printNum("b");
            }
        });

        t1.start();
        t2.start();
    }
}
