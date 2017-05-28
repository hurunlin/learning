/**
 * Project Name: demo-test
 * File Name: UserMapper.java
 * Package Name: cn.com.hrl.dao
 * Date: 2017-05-26 18:22
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.dao;

import cn.com.hrl.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: UserMapper <br>
 * Function: <br>
 * Date:  2017-05-26 18:22 <br>
 */
public interface UserMapper {
    @Insert("INSERT INTO tb_user(username, password) VALUES(#{username},#{password})")
    public int insertUser(@Param("username") String username, @Param("password") String password);

    /**
     * 插入用户，并将主键设置到user中
     * 注意：返回的是数据库影响条数，即1
     */
    public int insertUserWithBackId(User user);
}
