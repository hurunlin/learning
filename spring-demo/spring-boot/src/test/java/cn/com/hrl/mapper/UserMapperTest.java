package cn.com.hrl.mapper;

import cn.com.hrl.entity.UserEntity;
import cn.com.hrl.enums.UserSexEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper UserMapper;

    @Test
    public void testInsert() throws Exception {

        /**
         * 插入一百万数据
         */
        for (int i = 0; i < 1000000; i++) {
            UserMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
        }
        Assert.assertEquals(3, UserMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<UserEntity> users = UserMapper.getAll();
        System.out.println("==========================");
        for (UserEntity entity : users) {
            System.out.println("===" + entity.toString() + "===");
        }
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