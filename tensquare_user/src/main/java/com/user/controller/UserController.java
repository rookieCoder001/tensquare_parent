package com.user.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.user.pojo.User;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务对外接口
 *
 * @Author luo
 * @Date 2020/4/4 22:22
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {

        User u = userService.login(user);
        if (u == null) {
            //登录失败
            return new Result(true, StatusCode.ERROR, "登录失败");
        }
        return new Result(true, StatusCode.OK, "登录成功");
    }


    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "登录成功",userService.findById(id));
    }

}
