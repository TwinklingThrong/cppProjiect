package com.cpp.controller;

import com.cpp.pojo.LoginInfo;
import com.cpp.pojo.Result;
import com.cpp.pojo.User;
import com.cpp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sun.activation.registries.LogSupport.log;

@Slf4j
@RestController
public class LoginRegisterController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log(user.toString());
        LoginInfo info = userService.login(user);
        if (info != null){
            return Result.success(info);
        }else {
            return Result.error("用户名或密码错误");
        }

    }
    @PostMapping("/register")
    public Result insert(@RequestBody User user) {
        log.info(user.toString());
        userService.insert(user);
        return Result.success();
    }
}