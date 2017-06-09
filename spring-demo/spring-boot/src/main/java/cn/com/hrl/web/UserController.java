package cn.com.hrl.web;

import cn.com.hrl.entity.UserEntity;
import cn.com.hrl.service.UserDataSourceSlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserDataSourceSlaveService userDataSourceSlaveService;
	

    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=userDataSourceSlaveService.getUserById(1);
        return user;
    }
}