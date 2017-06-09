/**
 * Project Name: learning
 * File Name: UserServiceImpl.java
 * Package Name: cn.com.hrl.service.impl
 * Date: 2017-06-09 9:31
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.service.impl;

import cn.com.hrl.entity.UserEntity;
import cn.com.hrl.mapper.UserXmlMapper;
import cn.com.hrl.service.UserXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImpl <br>
 * Function: <br>
 * Date:  2017-06-09 9:31 <br>
 */
@Service("userXmlService")
public class UserXmlServiceImpl implements UserXmlService {

    @Autowired
    private UserXmlMapper userXmlMapper;

    public UserEntity getUserById(int id) {
        return userXmlMapper.getUserById(id);
    }
}
