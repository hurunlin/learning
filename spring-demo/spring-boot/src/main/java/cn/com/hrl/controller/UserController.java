/**
 * @Title: UserController
 * @Package cn.com.hrl.controller
 * @Description: 用户接口
 * @author hu.rl
 * @date 2017/6/7 21:17
 * @version V1.0
 */
package cn.com.hrl.controller;

import cn.com.hrl.mapper.UserMapper;
import cn.com.hrl.pojo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 用户接口
 * @author hu.rl
 * @date 2017/6/7 21:17
 */
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/getUsers")
    public List<UserEntity> getUsers() {
        List<UserEntity> users=userMapper.getAll();
        return users;
    }

    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
        UserEntity user=userMapper.getOne(id);
        return user;
    }

    @RequestMapping("/add")
    public void save(UserEntity user) {
        userMapper.insert(user);
    }

    @RequestMapping(value="update")
    public void update(UserEntity user) {
        userMapper.update(user);
    }

    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userMapper.delete(id);
    }
}
