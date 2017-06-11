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

import cn.com.hrl.database.mybatis.ReadOnlyConnection;
import cn.com.hrl.entity.UserEntity;
import cn.com.hrl.mapper.UserDataSourceXmlMapper;
import cn.com.hrl.service.UserDataSourceSlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImpl <br>
 * Function: <br>
 * Date:  2017-06-09 9:31 <br>
 */
@Service("userDataSourceSlaveService")
public class UserDataSourceSlaveServiceImpl implements UserDataSourceSlaveService {

    @Autowired
    private UserDataSourceXmlMapper userDataSourceXmlMapper;

    @ReadOnlyConnection
    public UserEntity getUserById(int id) {
        return userDataSourceXmlMapper.getUserById(id);
    }
}
