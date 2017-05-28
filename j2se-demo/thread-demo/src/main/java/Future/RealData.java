package Future;

/**
 * 真实数据类
 */
public class RealData implements Data {

    // 定义具体返回对象(此处用Sting代替)
    public String resultData;

    /**
     * 利用构造方法实现具体业务(此方法可单独实现此处为了方便直接写在构造方法中)
     * @param param
     * @return
     */
    public RealData(String param) {
        StringBuffer data = new StringBuffer();
        try {
            System.out.println("具体业务实现查询订单并返回!!");
            Thread.sleep(5000);
            data.append(param);
            data.append("+此处为具体实现业务处理完成之后返回");
            System.out.println("业务处理完成!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 具体实现返回给全局ResultData
        resultData = data.toString();
    }


    /**
     * 当初始化类完成之后那么所要返回的resutData已经更新,可调用此方法进行获取
     * @return
     */
    public String queryOrder() {
        return resultData;
    }

}
