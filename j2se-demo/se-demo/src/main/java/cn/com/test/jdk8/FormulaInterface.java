package cn.com.test.jdk8;

/**
 * Created by hurunlin on 2017/8/15.
 */
public interface FormulaInterface {
    public double test1();

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
