/**
 * @Title: UserTest
 * @Package java.cn.com.hrl.mapper
 * @Description: 测试类
 * @author hu.rl
 * @date 2017/6/7 21:33
 * @version V1.0
 */
package cn.com.hrl.mapper;

import cn.com.hrl.enums.UserSexEnum;
import cn.com.hrl.pojo.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName: UserTest
 * @Description: 测试类
 * @author hu.rl
 * @date 2017/6/7 21:33
 */

@RunWith(SpringRunner.class)
@SpringBootConfiguration
@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper UserMapper;

    @Test
    public void testInsert() throws Exception {
        UserMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
        UserMapper.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
        UserMapper.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

        Assert.assertEquals(3, UserMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<UserEntity> users = UserMapper.getAll();
        System.out.println("==========================");
        System.out.println("==="+users.toString()+"===");
        System.out.println("==========================");
    }


    @Test
    public void testUpdate() throws Exception {
        UserEntity user = UserMapper.getOne(3l);
        System.out.println(user.toString());
        user.setNickName("neo");
        UserMapper.update(user);
        Assert.assertTrue(("neo".equals(UserMapper.getOne(3l).getNickName())));
    }

}
