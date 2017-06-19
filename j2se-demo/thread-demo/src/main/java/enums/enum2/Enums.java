/**
 * Project Name: learning
 * File Name: T.java
 * Package Name: enums.enum2
 * Date: 2017-06-19 18:00
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package enums.enum2;

import java.util.Random;

/**
 * ClassName: T <br>
 * Function: <br>
 * Date:  2017-06-19 18:00 <br>
 */
public class Enums {
    private static Random rand = new Random(47);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
}
