/**
 * Project Name: learning
 * File Name: UserService.java
 * Package Name: cn.com.hrl.service
 * Date: 2017-06-09 9:30
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.service;

import cn.com.hrl.entity.UserEntity;

/**
 * ClassName: UserService <br>
 * Function: <br>
 * Date:  2017-06-09 9:30 <br>
 */

public interface UserService {
    /**
     * 获取用户对象
     * @param id
     * @return
     */
    public UserEntity getOne(Long id);
}
