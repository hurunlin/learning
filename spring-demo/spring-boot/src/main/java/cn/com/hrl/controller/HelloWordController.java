/**
 * Project Name: demo-test
 * File Name: HelloWordController.java
 * Package Name: Controller
 * Date: 2017-05-24 10:48
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: HelloWordController <br>
 * Function: <br>
 * Date:  2017-05-24 10:48 <br>
 */
@RestController
@ComponentScan(basePackages= "cn.com.hrl.controller")
@EnableAutoConfiguration
public class HelloWordController {

    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "hello words";
    }

    @RequestMapping("/hello/{name}")
    @ResponseBody
    public String index(@PathVariable String name) {

        if (null == name) {
            name = "boy";
        }
        return "hello world " + name;
    }
}
