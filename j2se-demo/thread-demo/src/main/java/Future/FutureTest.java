package Future;

/**
 * 测试类
 * 注:本示例用于模拟future模式底层的实现思想.如要使用此模式请使用JDK本身对future的支持
 */
public class FutureTest {


    /**
     * 1:future的思想在于让任务并行去执行,比如说:可能需要跑多个定时任务,那么可以将每一个定时任务同时去执行.
     * 2:将每一个定时任务的到的结果进行处理得到最终结果,这样方式可以大量缩小每一个整个任务的完成时间大大提高处理效率.
     * 3:future只适用于每个任务都是不相干的情况,也就是说得到[定时任务2]的结果不需要[定时任务1]的结果参与运算
     */
    public static void main(String[] args) {


        long currStartTime = System.currentTimeMillis();
        System.out.println("-------------------执行前时间戳-------------------:" + currStartTime);

        // 此处模拟处理四个业务处理的线程.每一个睡眠5秒,如果是非并行执行需要4*5=20秒,使用并行情况下只需5秒
        FutureClient client = new FutureClient();
        // 初始化第一个线程
        Data data1 = client.reqQuery("定时任务1!!");
        // 初始化第二个任务
        Data data2 = client.reqQuery("定时任务2!!");
        // 初始化第三个任务
        Data data3 = client.reqQuery("定时任务3!!");
        // 初始化第三个任务
        Data data4 = client.reqQuery("定时任务4!!");

        String result1 = data1.queryOrder();
        String result2 = data2.queryOrder();
        String result3 = data3.queryOrder();
        String result4 = data3.queryOrder();

        // 处理所有任务的结果得到总的结果
        System.out.println(result1 + "\n" + result2 + "\n" + result3 + "\n" + result4);
        long currEndTime = System.currentTimeMillis();
        System.out.println("-------------------执行后时间戳-------------------:" + currEndTime);
        System.out.println("-------------------耗时:" + (currEndTime - currStartTime));
    }
}
