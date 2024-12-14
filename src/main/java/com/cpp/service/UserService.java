package com.cpp.service;

import com.cpp.pojo.Apply;
import com.cpp.pojo.LoginInfo;
import com.cpp.pojo.User;

public interface UserService {
    void insert(User user);

    LoginInfo login(User user);

    void update(User user);

    User getById(Integer id);

}
