/**
 * Project Name: learning
 * File Name: UserXmlMapperTest.java
 * Package Name: cn.com.hrl.mapper
 * Date: 2017-06-09 12:24
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.mapper;

import cn.com.hrl.Application;
import cn.com.hrl.service.UserDataSourceSlaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName: UserXmlMapperTest <br>
 * Function: 基于XML实现测试类<br>
 * Date:  2017-06-09 12:24 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserDataSourceMapperTest {


    @Resource
    private UserDataSourceSlaveService userDataSourceSlaveService;

    @Test
    public void test() {
        System.out.println(userDataSourceSlaveService.getUserById(2));
    }
}
