/**
 * Project Name: learning
 * File Name: UserXmlMapper.java
 * Package Name: cn.com.hrl.mapper
 * Date: 2017-06-09 9:41
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.mapper;

import cn.com.hrl.core.database.BaseMapper;
import cn.com.hrl.entity.UserEntity;

import java.util.List;

/**
 * ClassName: UserXmlMapper <br>
 * Function: 用户Mapper<br>
 * Date:  2017-06-09 9:41 <br>
 */
public interface UserXmlMapper extends BaseMapper<UserEntity> {
    /**
     * 插入用户信息
     * @param userEntity
     * @return
     */
    public int insertUser(UserEntity userEntity);

    /**
     * 获取所有用户信息
     * @return
     */
    public List<UserEntity> findUserAll();

    /**
     * 根据ID获取用户信息
     * @param id
     * @return
     */
    public UserEntity getUserById(int id);
}
