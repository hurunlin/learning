/**
 * Project Name: demo-test
 * File Name: UserDao.java
 * Package Name: cn.com.hrl.dao.impl
 * Date: 2017-05-26 18:24
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.dao.impl;

import cn.com.hrl.dao.UserMapper;
import cn.com.hrl.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClassName: UserDao <br>
 * Function: <br>
 * Date:  2017-05-26 18:24 <br>
 */
public class UserDao implements UserMapper {

    @Autowired
    private UserMapper userMapper;

    public int insertUser(String username, String password){
        return userMapper.insertUser(username, password);
    }

    public int insertUserWithBackId(User user){
        return userMapper.insertUserWithBackId(user);
    }
}
