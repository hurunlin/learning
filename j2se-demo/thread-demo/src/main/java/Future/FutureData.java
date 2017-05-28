package Future;

/**
 * 创建返回包装Future对象
 * 1:会先返回一个future对象给前端,当具体业务处理完成之后才会拿到真正查询的具体返回结果
 */
public class FutureData  implements Data{

    // 全局真实数据实例,当真实数据对象处初始化完成时此属性不为空.
    public RealData realData = null;

    // 定义业务是否处理完成标识
    public boolean isReady = false;

    /**
     * 获取订单信息
     * @return
     */
    public synchronized String queryOrder() {
        // 判断是否已经完成,如果在未完成时调用此方法那么此线程将会睡眠
        if(!isReady){
            try {
                // 当具体业务代码没有执行完成时睡眠此线程(不释放锁)
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 如果已经完成数据家在那么返回对象
        return realData.resultData;
    }

    /**
     * 处理完成之后给全局的对象赋值
     */
    public synchronized void setRealData(RealData realData){
        // 如果是已经业务处理完成就中断并返回
        if(isReady){
            return;
        }
        //当处理完成的对象赋值给更新前的对象
        this.realData = realData;
        // 更新处理标识
        isReady = true;
        // 当具体业务处理完成时唤醒该线程
        notify();
    }
}
