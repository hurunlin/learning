/**
 * Project Name: demo-test
 * File Name: TradeTransactionJMSNotifyHandler.java
 * Package Name: Disruptor
 * Date: 2017-04-21 11:10
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package Disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * ClassName: TradeTransactionJMSNotifyHandler <br>
 * Function: <br>
 * Date:  2017-04-21 11:10 <br>
 */
public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {

    }
}
