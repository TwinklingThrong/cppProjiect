package com.cpp.controller;

import com.cpp.pojo.LoginInfo;
import com.cpp.pojo.Result;
import com.cpp.pojo.User;
import com.cpp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
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
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user,HttpSession session){
        log(user.toString());
        LoginInfo info = userService.login(user);
        if (info != null){
            session.setAttribute("userId",user.getId());
            return Result.success(info);
        }else {
            return Result.error("用户名或密码错误");
        }

    }
    //注册
    @PostMapping("/register")
    public Result insert(@RequestBody User user) {
        log.info(user.toString());
        userService.insert(user);
        return Result.success();
    }
}
