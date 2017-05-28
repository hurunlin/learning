/**
 * Project Name: demo-test
 * File Name: TradeTransaction.java
 * Package Name: Disruptor
 * Date: 2017-04-21 11:12
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package Disruptor;

/**
 * ClassName: TradeTransaction <br>
 * Function: <br>
 * Date:  2017-04-21 11:12 <br>
 */
public class TradeTransaction {
    private String id;//交易ID
    private double price;//交易金额

    public TradeTransaction() {
    }
    public TradeTransaction(String id, double price) {
        super();
        this.id = id;
        this.price = price;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
