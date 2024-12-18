package com.cpp.service.impl;

import com.cpp.mapper.UserMapper;
import com.cpp.pojo.Apply;
import com.cpp.pojo.LoginInfo;
import com.cpp.pojo.User;
import com.cpp.service.UserService;
import com.cpp.utils.LingPai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //注册
    @Override
    @Transactional
    public void insert(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }
    //登录
    @Override
    public LoginInfo login(User user) {
        Map<String, Object> map = new HashMap<>();
        User u = userMapper.loginByUserNamePassword(user);
        if (u != null) {
            map.put("id", u.getId());
            map.put("username", u.getUsername());
            return new LoginInfo(u.getId(), u.getUsername(), u.getPassword(), LingPai.generateToken(map), u.getRole());
        }else if (u == null){
            User u1 = userMapper.loginByPhonePassword(user);
            if (u1 == null){
                return null;
            }
            map.put("id", u1.getId());
            map.put("username", u1.getUsername());
            return new LoginInfo(u1.getId(), u1.getUsername(), u1.getPassword(), LingPai.generateToken(map), u1.getRole());
        }else {
        return null;
        }
    }

    //通过id找用户
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);

    }


    //修改个人信息
    @Override
    public void update(User user) {
        userMapper.updataById(user);
    }

}
