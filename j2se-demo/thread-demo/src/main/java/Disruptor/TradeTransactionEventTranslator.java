/**
 * Project Name: demo-test
 * File Name: TradeTransactionEventTranslator.java
 * Package Name: Disruptor
 * Date: 2017-04-21 11:13
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package Disruptor;

import com.lmax.disruptor.EventTranslator;

import java.util.Random;

/**
 * ClassName: TradeTransactionEventTranslator <br>
 * Function: <br>
 * Date:  2017-04-21 11:13 <br>
 */
public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
    private Random random=new Random();

    public void translateTo(TradeTransaction event, long sequence) {
        this.generateTradeTransaction(event);
    }
    private TradeTransaction generateTradeTransaction(TradeTransaction trade){
        trade.setPrice(random.nextDouble()*9999);
        return trade;
    }
}
