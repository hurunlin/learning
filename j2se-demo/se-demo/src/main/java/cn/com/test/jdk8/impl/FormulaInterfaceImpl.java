package cn.com.test.jdk8.impl;

import cn.com.test.jdk8.FormulaInterface;

/**
 * Created by hurunlin on 2017/8/15.
 */
public class FormulaInterfaceImpl implements FormulaInterface {

    // 接口直接调用默认方法
    @Override
    public double test1() {
        return sqrt(100);
    }
}
