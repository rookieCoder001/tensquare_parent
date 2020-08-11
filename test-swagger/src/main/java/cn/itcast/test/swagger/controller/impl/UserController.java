package cn.itcast.test.swagger.controller.impl;

import cn.itcast.test.swagger.bean.Demo;
import cn.itcast.test.swagger.bean.User;
import cn.itcast.test.swagger.controller.UserControllerApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController implements UserControllerApi {

    @PostMapping("/findList/{page}")
    public User<Demo> findList(@PathVariable Long page, Long size, User<Demo> user) {
        System.out.println(page);
        System.out.println(size);
        User<Demo> user1 = new User<Demo>();
        user1.setId(page*size*1L);
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword()+"====");
        return user1;
    }
}
