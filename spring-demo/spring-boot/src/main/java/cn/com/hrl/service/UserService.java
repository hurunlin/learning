/**
 * Project Name: demo-test
 * File Name: UserService.java
 * Package Name: cn.com.hrl.service
 * Date: 2017-05-26 18:26
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.service;

import cn.com.hrl.dao.impl.UserDao;
import cn.com.hrl.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserService <br>
 * Function: <br>
 * Date:  2017-05-26 18:26 <br>
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean addUser(String username, String password) {
        return userDao.insertUser(username, password) == 1 ? true : false;
    }

    public User addUserWithBackId(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.insertUserWithBackId(user);//该方法后，主键已经设置到user中了
        return user;
    }
}
