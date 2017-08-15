package Thread.Thread1;

/**
 * Created by hurunlin on 2017/8/10.
 */
public class Test {

    public static  boolean isno = true;

    public static void main(String[] args) {
        Test t = new Test();
        t.test1();
        //t.test2();
        isno = false;
    }

    public void test1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isno) {
                    System.out.println("111111111111111");
                }
                System.out.println("线程实现");
            }
        }).start();
    }
/*

    public void test2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("22222222222222222");
                isno = false;
            }

        }).start();
    }

*/

}
