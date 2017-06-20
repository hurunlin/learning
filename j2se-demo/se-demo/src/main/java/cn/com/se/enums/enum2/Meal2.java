/**
 * Project Name: learning
 * File Name: Meal2.java
 * Package Name: enums.enum2
 * Date: 2017-06-19 18:01
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.se.enums.enum2;

/**
 * ClassName: Meal2 <br>
 * Function: <br>
 * Date:  2017-06-19 18:01 <br>
 */
public enum Meal2 {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;

    private Meal2(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }

    public Food randomSelection() {
        return Enums.random(values);
    }
}
