/**
 * @Title: Test1
 * @Package cn.com.se.enums.Test1
 * @Description: 拉姆达表达式实现线程
 * @author hu.rl
 * @date 2017/8/30 12:20
 * @version V1.0
 */
package cn.com.se.enums.Test1;

/**
 * @ClassName: Test1
 * @Description: 拉姆达表达式实现线程
 * @author hu.rl
 * @date 2017/8/30 12:20
 */
public class Test1 {


    public void test(){
        new Thread(() -> System.out.println("123456") ).start();
    }


}
