/**
 * Project Name: demo-test
 * File Name: TradeTransactionVasConsumer.java
 * Package Name: Disruptor
 * Date: 2017-04-21 11:13
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package Disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * ClassName: TradeTransactionVasConsumer <br>
 * Function: <br>
 * Date:  2017-04-21 11:13 <br>
 */
public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {

    }
}
