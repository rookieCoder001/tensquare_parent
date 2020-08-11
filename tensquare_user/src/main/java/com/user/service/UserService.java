package com.user.service;

import com.user.mapper.UserMapper;
import com.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 * @Author luo
 * @Date 2020/4/4 22:22
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param user
     * @return
     */
    public User login(User user){

       return userMapper.selectOne(user);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public User findById(String id){
        return userMapper.selectById(id);
    }

}
