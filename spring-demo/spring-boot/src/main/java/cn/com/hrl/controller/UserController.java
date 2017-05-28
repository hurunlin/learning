/**
 * Project Name: demo-test
 * File Name: UserController.java
 * Package Name: cn.com.hrl.controller
 * Date: 2017-05-26 18:30
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.controller;

import cn.com.hrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: UserController <br>
 * Function: <br>
 * Date:  2017-05-26 18:30 <br>
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test(){
        userService.addUser("hurunlin","111111");
        return "成功";
    }

}
