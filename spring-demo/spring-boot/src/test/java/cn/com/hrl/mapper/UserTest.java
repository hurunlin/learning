/**
 * Project Name: learning
 * File Name: UserTest.java
 * Package Name: cn.com.hrl.mapper
 * Date: 2017-06-09 9:33
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.mapper;

import cn.com.hrl.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName: UserTest <br>
 * Function: <br>
 * Date:  2017-06-09 9:33 <br>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    private UserService userService;

    @Test
    public void test(){
        System.out.println(userService.getOne(1L));
    }
}
