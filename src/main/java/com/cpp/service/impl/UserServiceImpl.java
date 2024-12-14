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

    @Override
    @Transactional
    public void insert(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public LoginInfo login(User user) {
        User u = userMapper.loginByUserNamePassword(user);
        if (u != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", u.getId());
            map.put("username", u.getUsername());
            return new LoginInfo(u.getId(), u.getUsername(), u.getPassword(), LingPai.generateToken(map), u.getRole());
        }
        return null;
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);

    }


    @Override
    public void update(User user) {
        userMapper.updataById(user);
    }

}
