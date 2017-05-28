package Future;

/**
 * future客户端类
 */
public class FutureClient {

    /**
     * 请求处理方法
     */
    public static Data reqQuery(final String param) {
        // 定义对象
        final FutureData futureData = new FutureData();
        // 单独启动线程异步处理具体业务
        new Thread(new Runnable() {
            public void run() {
                // 初始化真实数据处理类
                RealData realData = new RealData(param);
                // 将真实数据对象更新
                futureData.setRealData(realData);
            }
        }).start();

        return futureData;
    }
}
