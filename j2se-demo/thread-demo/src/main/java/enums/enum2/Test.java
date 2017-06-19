/**
 * Project Name: learning
 * File Name: Test.java
 * Package Name: enums.enum2
 * Date: 2017-06-19 18:06
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package enums.enum2;

/**
 * ClassName: Test <br>
 * Function: <br>
 * Date:  2017-06-19 18:06 <br>
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (Meal2 meal : Meal2.values()) {
                Food food = meal.randomSelection();
                System.out.println(food);
            }
            System.out.println("---");
        }
    }
}
