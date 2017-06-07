package cn.com.hrl; /**
 * Project Name: demo-test
 * File Name: Application.java
 * Package Name: PACKAGE_NAME
 * Date: 2017-05-24 10:46
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: Application <br>
 * Function: <br>
 * Date:  2017-05-24 10:46 <br>
 */
@SpringBootApplication
@MapperScan("cn.com.hrl.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
