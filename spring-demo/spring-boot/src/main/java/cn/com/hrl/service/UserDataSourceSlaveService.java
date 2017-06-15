/**
 * Project Name: learning
 * File Name: UserDataSourceSlaveService.java
 * Package Name: cn.com.hrl.service
 * Date: 2017-06-09 14:16
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.service;

import cn.com.hrl.entity.UserEntity;

/**
 * ClassName: UserDataSourceSlaveService <br>
 * Function: 用户从数据源<br>
 * Date:  2017-06-09 14:16 <br>
 */
public interface UserDataSourceSlaveService {

    /**
     * 从slave库中获取数据
     *
     * @param id
     * @return
     */
    public UserEntity getUserById(int id);
}
