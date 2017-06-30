/**
 * Project Name: learning
 * File Name: Test.java
 * Package Name: cn.com.se.enums
 * Date: 2017-06-28 10:56
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.se.enums;

/**
 * ClassName: Test <br>
 * Function: <br>
 * Date:  2017-06-28 10:56 <br>
 */
public class Test {

    class A {

        public void test() {
            System.out.println("test to A");
        }

        public A() {
            System.out.println("Before test to A Construction");
            test();
            System.out.println("After test to A Construction");
        }
    }


    class B extends A {

        public int a = 1;

        public void test() {
            System.out.println("test to B");
        }

        public B(int a) {
            a = a;
            test();
            System.out.println("After test to B Construction" + a);
        }

    }
}
